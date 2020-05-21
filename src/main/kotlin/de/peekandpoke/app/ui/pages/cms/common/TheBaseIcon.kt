package de.peekandpoke.app.ui.pages.cms.common

import de.peekandpoke.ultrajs.semanticui.SemanticIcon
import de.peekandpoke.ultrajs.semanticui.SemanticIconMarker

@SemanticIconMarker
val SemanticIcon.tb
    get() = TheBaseIcon(this)

@Suppress("FunctionName", "unused")
class TheBaseIcon(private val semantic: SemanticIcon) {

    @Suppress("EnumEntryName", "unused")
    enum class Name(val cls: String) {
        arrow_left_large("arrow-left-large"),
        arrow_right_large("arrow-right-large"),
        arrow_down("arrow-down"),
        arrow_right("arrow-right"),
        angle_down_large("angle-down-large"),
        angle_down_small("angle-down-small"),
        circle("circle"),
        circle_outline("circle-outline"),
        circle_times_outline_large("circle-times-outline-large"),
        circle_check_outline_large("circle-check-outline-large"),
        map_pin_a("map-pin-a"),
        map_pin_b("map-pin-b"),
        map_pin_c("map-pin-c"),
        map_pin_d("map-pin-d"),
        map_pin_e("map-pin-e"),
        map_pin_f("map-pin-f"),
        map_pin_g("map-pin-g"),
        map_pin_h("map-pin-h"),
        map_pin_i("map-pin-i"),
        map_pin_j("map-pin-j"),
        map_pin_k("map-pin-k"),
        circle_times_outline("circle-times-outline"),
        circle_check("circle-check"),
        circle_times("circle-times"),
        twitter("twitter"),
        linkedin("linkedin"),
        instagram("instagram"),
        facebook("facebook"),
        burgerMenu("burger-menu"),
        burgerMenuClose("burger-menu-close"),
    }

    fun render(name: Name) = semantic.render("tb-icon ${name.cls}")

    @SemanticIconMarker fun arrow_left_large() = render(Name.arrow_left_large)
    @SemanticIconMarker fun arrow_right_large() = render(Name.arrow_right_large)
    @SemanticIconMarker fun arrow_down() = render(Name.arrow_down)
    @SemanticIconMarker fun arrow_right() = render(Name.arrow_right)
    @SemanticIconMarker fun angle_down_large() = render(Name.angle_down_large)
    @SemanticIconMarker fun angle_down_small() = render(Name.angle_down_small)
    @SemanticIconMarker fun circle() = render(Name.circle)
    @SemanticIconMarker fun circle_outline() = render(Name.circle_outline)
    @SemanticIconMarker fun circle_times_outline_large() = render(Name.circle_times_outline_large)
    @SemanticIconMarker fun circle_check_outline_large() = render(Name.circle_check_outline_large)
    @SemanticIconMarker fun map_pin_a() = render(Name.map_pin_a)
    @SemanticIconMarker fun map_pin_b() = render(Name.map_pin_b)
    @SemanticIconMarker fun map_pin_c() = render(Name.map_pin_c)
    @SemanticIconMarker fun map_pin_d() = render(Name.map_pin_d)
    @SemanticIconMarker fun map_pin_e() = render(Name.map_pin_e)
    @SemanticIconMarker fun map_pin_f() = render(Name.map_pin_f)
    @SemanticIconMarker fun map_pin_g() = render(Name.map_pin_g)
    @SemanticIconMarker fun map_pin_h() = render(Name.map_pin_h)
    @SemanticIconMarker fun map_pin_i() = render(Name.map_pin_i)
    @SemanticIconMarker fun map_pin_j() = render(Name.map_pin_j)
    @SemanticIconMarker fun map_pin_k() = render(Name.map_pin_k)
    @SemanticIconMarker fun circle_times_outline() = render(Name.circle_times_outline)
    @SemanticIconMarker fun circle_check() = render(Name.circle_check)
    @SemanticIconMarker fun circle_times() = render(Name.circle_times)
    @SemanticIconMarker fun twitter() = render(Name.twitter)
    @SemanticIconMarker fun linkedin() = render(Name.linkedin)
    @SemanticIconMarker fun instagram() = render(Name.instagram)
    @SemanticIconMarker fun facebook() = render(Name.facebook)
    @SemanticIconMarker fun burgerMenu() = render(Name.burgerMenu)
    @SemanticIconMarker fun burgerMenuClose() = render(Name.burgerMenuClose)
}
