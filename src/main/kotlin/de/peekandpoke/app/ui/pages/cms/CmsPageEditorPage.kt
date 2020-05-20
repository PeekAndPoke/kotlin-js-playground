package de.peekandpoke.app.ui.pages.cms

import de.peekandpoke.app.Api
import de.peekandpoke.app.domain.cms.CmsPageModel
import de.peekandpoke.app.domain.domainCodec
import de.peekandpoke.app.ui.Theme
import de.peekandpoke.app.ui.components.forms.CheckboxField
import de.peekandpoke.app.ui.components.forms.FormComponent
import de.peekandpoke.app.ui.components.forms.SelectField
import de.peekandpoke.app.ui.components.forms.TextField
import de.peekandpoke.app.ui.components.forms.validation.NotBlank
import de.peekandpoke.app.utils.modifyAt
import de.peekandpoke.app.utils.removeAt
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.styling.css
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.icon
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.html.FlowContent
import kotlinx.html.Tag
import kotlinx.html.title

@Suppress("FunctionName")
fun Tag.CmsPageEditor(id: String) = comp(CmsPageEditorPage.Props(id)) { CmsPageEditorPage(it) }

class CmsPageEditorPage(ctx: Ctx<Props>) : FormComponent<CmsPageEditorPage.Props>(ctx) {

    data class Props(
        val id: String
    )

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /** The original version */
    private var original by property<CmsPageModel?>(null) { draft = it }

    /** The draft version that we are editing */
    private var draft by property<CmsPageModel?>(null) {
        it?.let {
            console.log("CmsPage draft", domainCodec.stringify(CmsPageModel.serializer(), it))
        }
    }

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        reload()
    }

    override fun VDom.render() {
        ui.basic.inverted.blue.segment {
            css(Theme.Pages.headerPadding)

            ui.grid {
                ui.ten.wide.column {
                    ui.header H1 {
                        css(Theme.Pages.whiteText)
                        icon.small.edit()
                        +"Edit Cms Page '${original?.name}'"

                    }
                }
                ui.right.aligned.six.wide.column {
                    ui.inverted.button Button {
                        +"Save"
                        disabled = !isValid || original == draft
                        onClick { save() }
                    }
                }
            }
        }

        ui.basic.segment {
            basicInfo()
            meta()
        }
    }

    private fun reload() {
        GlobalScope.launch {
            Api.cms
                .getPage(props.id)
                .collect { original = it.data!! }
        }
    }

    private fun save() {
        draft?.let {
            GlobalScope.launch {
                Api.cms
                    .savePage(it.id, it)
                    .collect { original = it.data!! }
            }
        }
    }

    private fun FlowContent.basicInfo() {
        draft?.apply {
            ui.segment {
                ui.form {
                    ui.three.fields {
                        TextField({ name }, { draft = copy(name = it) }) {
                            label = "Name"
                            accepts(NotBlank)
                        }
                        TextField({ uri }, { draft = copy(uri = it) }) {
                            label = "Uri"
                            // TODO: validate the uri does not yet exist
                            accepts(NotBlank)
                        }
                        TextField({ tags }, { draft = copy(tags = it) }) {
                            label = "Tags"
                            accepts(NotBlank)
                        }
                    }
                }
            }
        }
    }

    private fun FlowContent.meta() {

        val languageOptions: SelectField.PropsBuilder<String>.() -> Unit = {
            option("en", "en") { +"en" }
            option("de", "de") { +"de" }
        }

        draft?.apply {
            ui.segment {
                ui.form {
                    ui.header H3 { +"Meta data" }

                    ui.three.fields {
                        TextField(
                            { meta.description },
                            { draft = copy(meta = meta.copy(description = it)) }
                        ) {
                            label = "Name"
                            accepts(NotBlank)
                        }

                        CheckboxField(
                            { meta.robotsIndex },
                            { draft = copy(meta = meta.copy(robotsIndex = it)) }
                        ) {
                            label = "Robots Index"
                        }

                        CheckboxField(
                            { meta.robotsFollow },
                            { draft = copy(meta = meta.copy(robotsFollow = it)) }
                        ) {
                            label = "Robots Follow"
                        }
                    }

                    ui.header H3 { +"Alternate Languages" }

                    ui.basic.segment {
                        ui.four.column.grid {

                            meta.alternateLanguages.forEachIndexed { index, alternateLanguage ->
                                ui.column {
                                    ui.top.attached.segment {
                                        SelectField(
                                            { alternateLanguage.language },
                                            { new ->
                                                draft = copy(
                                                    meta = meta.copy(
                                                        alternateLanguages = meta.alternateLanguages.modifyAt(index) {
                                                            it.copy(language = new)
                                                        }
                                                    )
                                                )
                                            }
                                        ) {
                                            label = "Language"
                                            languageOptions()
                                        }

                                        // TODO: we need an UrlInputField ... that also checks if the url is valid
                                        TextField(
                                            { alternateLanguage.url.url },
                                            { new ->
                                                draft = copy(
                                                    meta = meta.copy(
                                                        alternateLanguages = meta.alternateLanguages.modifyAt(index) {
                                                            it.copy(url = it.url.copy(url = new))
                                                        }
                                                    )
                                                )
                                            }
                                        ) {
                                            label = "Language"
                                        }
                                    }

                                    ui.bottom.attached.segment {
                                        ui.fluid.icon.button {
                                            title = "Remove"
                                            icon.close()
                                            onClick {
                                                draft = copy(
                                                    meta = meta.copy(
                                                        alternateLanguages = meta.alternateLanguages.removeAt(index)
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            ui.column {
                                ui.placeholder.raised.segment {
                                    ui.icon.header { icon.plus() }
                                    onClick {
                                        draft = copy(
                                            meta = meta.copy(
                                                alternateLanguages = meta.alternateLanguages.plus(CmsPageModel.AlternateLanguage())
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
