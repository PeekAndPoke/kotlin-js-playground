package de.peekandpoke.mithrilkt.renderer

import de.peekandpoke.jshelper.js
import de.peekandpoke.mithrilkt.components.Component

val symInstance = js("(Symbol('instance'))")

val LowLevelComponent = mapOf(
    // Low level hook into Mithrils 'oninit' method.
    // See: https://mithril.js.org/lifecycle-methods.html#oninit
    "oninit" to { vnode: dynamic ->
//        console.log("oninit", vnode)

        if (!vnode.state[symInstance]) {
            val instance = vnode.attrs.creator(vnode.attrs.ctx) as Component<*, *>
            vnode.state[symInstance] = instance
        }

        vnode.state.state = vnode.state[symInstance].state
    },

    // Low level hook into Mithrils 'onbeforeupdate' method.
    // We use this on to propagate the next Ctx / Props to existing components
    // See: https://mithril.js.org/lifecycle-methods.html#onbeforeupdate
    "onbeforeupdate" to { vnode: dynamic ->
        (vnode.state[symInstance] as Component<*, *>)._nextCtx(vnode.attrs.ctx)
    },

    "onremove" to { vnode: dynamic ->
        (vnode.state[symInstance] as Component<*, *>).onRemove()
    },

    // Bridging from Mithrils 'view' method to Component.render()
    "view" to { vnode: dynamic ->
        (vnode.state[symInstance] as Component<*, *>)._internalRender()
    }
).js
