package de.peekandpoke.app.ui.pages.cms.forms

import de.peekandpoke.app.domain.cms.CmsPageModel
import de.peekandpoke.app.ui.components.forms.*
import de.peekandpoke.app.ui.components.forms.validation.NotBlank
import de.peekandpoke.app.ui.components.forms.validation.NotEmpty
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.icon
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.FlowContent
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.CmsPageMetaForm(meta: CmsPageModel.Meta, onChange: (CmsPageModel.Meta) -> Unit) =
    comp(CmsPageMetaFormComponent.Props(meta, onChange)) { CmsPageMetaFormComponent(it) }

class CmsPageMetaFormComponent(ctx: Ctx<Props>) : Component<CmsPageMetaFormComponent.Props>(ctx) {

    data class Props(
        val original: CmsPageModel.Meta,
        val onChange: (CmsPageModel.Meta) -> Unit
    )

    // TODO: make the language options configurable
    private val languageOptions: SelectField.PropsBuilder<String>.() -> Unit = {
        option("", "") { +"---" }
        option("en") { +"en" }
        option("de") { +"de" }
    }

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {

        ui.form {
            basics()
            ui.divider {}
            alternateLanguages()
        }
    }

    private fun FlowContent.basics() {

        props.original.apply {
            ui.fields {
                TextField(description, { props.onChange(copy(description = it)) }) {
                    label = "Description"
                    appearance = { ten.wide }
                    accepts(NotBlank)
                }

                CheckboxField(robotsIndex, { props.onChange(copy(robotsIndex = it)) }) {
                    label = "Robots Index"
                    appearance = { three.wide }
                }

                CheckboxField(robotsFollow, { props.onChange(copy(robotsFollow = it)) }) {
                    label = "Robots Follow"
                    appearance = { three.wide }
                }
            }
        }
    }

    private fun FlowContent.alternateLanguages() {
        props.original.apply {
            ui.header H3 { +"Alternate Languages" }

            ui.basic.segment {
                ui.four.column.grid {

                    ListField(alternateLanguages, { props.onChange(copy(alternateLanguages = it)) }) {
                        renderItem { ctx ->
                            renderAlternateLanguage(ctx)
                        }
                        renderAdd { ctx ->
                            ui.column {
                                ui.placeholder.raised.segment {
                                    ui.icon.header { icon.plus() }
                                    onClick {
                                        ctx.add(CmsPageModel.AlternateLanguage())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun FlowContent.renderAlternateLanguage(
        ctx: ListFieldComponent.ItemCtx<CmsPageModel.AlternateLanguage>
    ) {

        ctx.item.apply {

            ui.column {
                ui.top.attached.segment {
                    SelectField(language, { ctx.modify(copy(language = it)) }) {
                        label = "Language"
                        languageOptions()
                        accepts(NotEmpty)
                    }

                    LinkUrlEditor("Url", url) { ctx.modify(copy(url = it)) }
                }

                ui.bottom.attached.segment {
                    ui.basic.fluid.buttons {
                        leftRemoveRightButtons(ctx)
                    }
                }
            }
        }
    }
}
