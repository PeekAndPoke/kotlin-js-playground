package de.peekandpoke.app.ui.pages.cms.forms

import de.peekandpoke.app.domain.cms.CmsPageModel
import de.peekandpoke.app.ui.components.forms.CheckboxField
import de.peekandpoke.app.ui.components.forms.SelectField
import de.peekandpoke.app.ui.components.forms.TextField
import de.peekandpoke.app.ui.components.forms.validation.NotBlank
import de.peekandpoke.app.utils.modifyAt
import de.peekandpoke.app.utils.removeAt
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.icon
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.FlowContent
import kotlinx.html.Tag
import kotlinx.html.title

@Suppress("FunctionName")
fun Tag.CmsPageMetaForm(meta: CmsPageModel.Meta, onChange: (CmsPageModel.Meta) -> Unit) =
    comp(CmsPageMetaFormComponent.Props(meta, onChange)) { CmsPageMetaFormComponent(it) }

class CmsPageMetaFormComponent(ctx: Ctx<Props>) : Component<CmsPageMetaFormComponent.Props>(ctx) {

    data class Props(
        val original: CmsPageModel.Meta,
        val onChange: (CmsPageModel.Meta) -> Unit
    )

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var draft by property(props.original) {
        // Propagate changes to outer forms
        props.onChange(it)
    }

    // TODO: make the language options configurable
    private val languageOptions: SelectField.PropsBuilder<String>.() -> Unit = {
        option("en", "en") { +"en" }
        option("de", "de") { +"de" }
    }

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {

        ui.header H3 { +"Meta data" }

        ui.form {
            basics()
            alternateLanguages()
        }
    }

    private fun FlowContent.basics() {

        draft.apply {
            ui.fields {
                TextField({ description }, { draft = copy(description = it) }) {
                    label = "Description"
                    appearance = { ten.wide }
                    accepts(NotBlank)
                }

                CheckboxField({ robotsIndex }, { draft = copy(robotsIndex = it) }) {
                    label = "Robots Index"
                    appearance = { three.wide }
                }

                CheckboxField({ robotsFollow }, { draft = copy(robotsFollow = it) }) {
                    label = "Robots Follow"
                    appearance = { three.wide }
                }
            }
        }
    }

    private fun FlowContent.alternateLanguages() {
        draft.apply {
            ui.header H3 { +"Alternate Languages" }

            ui.basic.segment {
                ui.four.column.grid {
                    // Render all alternate language entries
                    alternateLanguages.forEachIndexed { idx, alt -> renderAlternateLanguage(idx, alt) }

                    // Render the add alternate language button
                    ui.column {
                        ui.placeholder.raised.segment {
                            ui.icon.header { icon.plus() }
                            onClick {
                                draft = copy(
                                    alternateLanguages = alternateLanguages.plus(CmsPageModel.AlternateLanguage())
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    private fun FlowContent.renderAlternateLanguage(index: Int, alternate: CmsPageModel.AlternateLanguage) {

        ui.column {
            ui.top.attached.segment {
                SelectField(
                    { alternate.language },
                    { new ->
                        draft = draft.copy(
                            alternateLanguages = draft.alternateLanguages.modifyAt(index) {
                                it.copy(language = new)
                            }
                        )
                    }
                ) {
                    label = "Language"
                    languageOptions()
                }

                TextField(
                    { alternate.url.url },
                    { new ->
                        draft = draft.copy(
                            alternateLanguages = draft.alternateLanguages.modifyAt(index) { alt ->
                                alt.copy(url = alt.url.copy(url = new))
                            }
                        )
                    }
                ) {
                    // TODO: we need an UrlInputField ... that also checks if the url is valid
                    accepts(NotBlank)
                    label = "Url"
                }
            }

            ui.bottom.attached.segment {
                ui.fluid.icon.button {
                    title = "Remove"
                    icon.close()
                    onClick {
                        draft = draft.copy(
                            alternateLanguages = draft.alternateLanguages.removeAt(index)
                        )
                    }
                }
            }
        }
    }
}
