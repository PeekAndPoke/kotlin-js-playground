package de.peekandpoke.app.ui.pages.cms.elements

import de.peekandpoke.app.domain.cms.elements.CmsElement
import de.peekandpoke.app.domain.cms.elements.HeroElement
import de.peekandpoke.app.ui.components.forms.SelectField
import de.peekandpoke.app.ui.components.forms.TextField
import de.peekandpoke.app.ui.pages.cms.forms.ElementStyleEditor
import de.peekandpoke.app.ui.pages.cms.forms.MarkdownEditor
import de.peekandpoke.app.ui.pages.cms.forms.patternOptions
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.HeroElementEditor(item: HeroElement, onChange: (CmsElement) -> Unit) =
    comp(HeroElementEditor.Props(item, onChange)) { HeroElementEditor(it) }

class HeroElementEditor(ctx: Ctx<Props>) : Component<HeroElementEditor.Props>(ctx) {

    data class Props(
        val item: HeroElement,
        val onChange: (CmsElement) -> Unit
    )

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var draft by property(props.item) { props.onChange(it) }

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        draft.apply {
            ui.segment {
                ui.header H5 { +props.item.elementDescription }

                ui.form {
                    ui.four.fields {
                        ElementStyleEditor(styling) { draft = copy(styling = it) }

                        SelectField({ layout }, { draft = copy(layout = it) }) {
                            label = "Layout"
                            option(HeroElement.Layout.FullSizeImage) { +"Full Size Image" }
                            option(HeroElement.Layout.ImageRight) { +"Image on the right" }
                        }

                        SelectField({ pattern }, { draft = copy(pattern = it) }) {
                            label = "Pattern"
                            patternOptions()
                        }
                    }

                    TextField({ headline }, { draft = copy(headline = it) }) {
                        label = "Headline"
                    }

                    MarkdownEditor("Text", text) { draft = copy(text = it) }
                }
            }
        }
    }
}
