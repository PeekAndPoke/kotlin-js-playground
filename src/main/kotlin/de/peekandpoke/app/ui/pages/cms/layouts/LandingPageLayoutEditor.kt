package de.peekandpoke.app.ui.pages.cms.layouts

import de.peekandpoke.app.domain.cms.layouts.LandingPageLayout
import de.peekandpoke.app.ui.pages.cms.elements.CmsElementEditor
import de.peekandpoke.app.utils.modifyAt
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.LandingPageLayoutEditor(layout: LandingPageLayout, onChange: (LandingPageLayout) -> Unit) =
    comp(LandingPageLayoutEditor.Props(layout, onChange)) { LandingPageLayoutEditor(it) }

class LandingPageLayoutEditor(ctx: Ctx<Props>) : Component<LandingPageLayoutEditor.Props>(ctx) {

    data class Props(
        val original: LandingPageLayout,
        val onChange: (LandingPageLayout) -> Unit
    )

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var draft by property(props.original) {
        // Propagate changes to outer forms
        props.onChange(it)
    }

    ////  IMPL   ///////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        ui.basic.segment {
            ui.segment {
                ui.header H3 { +"Landing Page Layout" }
            }

            draft.elements.forEachIndexed { idx, element ->
                CmsElementEditor(element) { modified ->
                    draft = draft.copy(
                        elements = draft.elements.modifyAt(idx) { modified }
                    )
                }
            }
        }
    }
}
