package de.peekandpoke.kraft.vdom.mithril

import de.peekandpoke.jshelper.js
import de.peekandpoke.kraft.components.Component
import kotlinx.html.ObjectName

private val symInstance = js("(Symbol('instance'))")

private var componentCounter = 0

val MithrilLowLevelComponent = mapOf(
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

    // Low level hook into Mithrils 'oncreate' method.
    // See: https://mithril.js.org/lifecycle-methods.html#oncreate
    "oncreate" to { vnode: dynamic ->
        // Setting the dom node on the Component
        vnode.state[symInstance].dom = vnode.dom
    },

    // Low level hook into Mithrils 'onbeforeupdate' method.
    // We use this on to propagate the next Ctx / Props to existing components
    // See: https://mithril.js.org/lifecycle-methods.html#onbeforeupdate
    "onbeforeupdate" to { vnode: dynamic ->

        vnode.state.state = vnode.state[symInstance].state

        true.run {
            (vnode.state[symInstance] as Component<*, *>)._nextCtx(vnode.attrs.ctx)
        }
    },

    "onremove" to { vnode: dynamic ->
        (vnode.state[symInstance] as Component<*, *>)._internalOnRemove()
    },

    // Bridging from Mithrils 'view' method to Component.render()
    "view" to { vnode: dynamic ->
        (vnode.state[symInstance] as Component<*, *>)._internalRender()
    }
).js
