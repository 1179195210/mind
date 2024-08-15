/**
 * @license BSD-3-Clause
 * @copyright 2014-2022 hizzgdev@163.com
 *
 * Project Home:
 *   https://github.com/hizzgdev/jsmind/
 */
!function () {
    "use strict";
    if (!jsMind) throw new Error("jsMind is not defined");
    const t = jsMind, e = t.$, i = "getSelection" in e.w ? function () {
        e.w.getSelection().removeAllRanges()
    } : function () {
        e.d.selection.empty()
    }, s = 5, o = 500, n = 80;

    class h {
        constructor(t) {
            this.jm = t, this.e_canvas = null, this.canvas_ctx = null, this.shadow = null, this.shadow_w = 0, this.shadow_h = 0, this.active_node = null, this.target_node = null, this.target_direct = null, this.client_w = 0, this.client_h = 0, this.offset_x = 0, this.offset_y = 0, this.hlookup_delay = 0, this.hlookup_timer = 0, this.capture = !1, this.moved = !1
        }

        init() {
            this._create_canvas(), this._create_shadow(), this._event_bind()
        }

        resize() {
            this.jm.view.e_nodes.appendChild(this.shadow), this.e_canvas.width = this.jm.view.size.w, this.e_canvas.height = this.jm.view.size.h
        }

        _create_canvas() {
            var t = e.c("canvas");
            this.jm.view.e_panel.appendChild(t);
            var i = t.getContext("2d");
            this.e_canvas = t, this.canvas_ctx = i
        }

        _create_shadow() {
            var t = e.c("jmnode");
            t.style.visibility = "hidden", t.style.zIndex = "3", t.style.cursor = "move", t.style.opacity = "0.7", this.shadow = t
        }

        reset_shadow(t) {
            var e = this.shadow.style;
            this.shadow.innerHTML = t.innerHTML, e.left = t.style.left, e.top = t.style.top, e.width = t.style.width, e.height = t.style.height, e.backgroundImage = t.style.backgroundImage, e.backgroundSize = t.style.backgroundSize, e.transform = t.style.transform, this.shadow_w = this.shadow.clientWidth, this.shadow_h = this.shadow.clientHeight
        }

        show_shadow() {
            this.moved || (this.shadow.style.visibility = "visible")
        }

        hide_shadow() {
            this.shadow.style.visibility = "hidden"
        }

        _magnet_shadow(t) {
            t && (this.canvas_ctx.lineWidth = s, this.canvas_ctx.strokeStyle = "rgba(0,0,0,0.3)", this.canvas_ctx.lineCap = "round", this._clear_lines(), this._canvas_lineto(t.sp.x, t.sp.y, t.np.x, t.np.y))
        }

        _clear_lines() {
            this.canvas_ctx.clearRect(0, 0, this.jm.view.size.w, this.jm.view.size.h)
        }

        _canvas_lineto(t, e, i, s) {
            this.canvas_ctx.beginPath(), this.canvas_ctx.moveTo(t, e), this.canvas_ctx.lineTo(i, s), this.canvas_ctx.stroke()
        }

        _lookup_close_node() {
            var t, e, i = this.jm.get_root(), o = i.get_location(), n = i.get_size(), h = o.x + n.w / 2,
                a = this.shadow_w, l = this.shadow_h, d = this.shadow.offsetLeft, c = this.shadow.offsetTop,
                r = d + a / 2 >= h ? jsMind.direction.right : jsMind.direction.left, _ = this.jm.mind.nodes, v = null,
                u = this.jm.layout, w = Number.MAX_VALUE, f = 0, m = null, g = null, p = null;
            for (var y in _) {
                var j, x;
                if ((v = _[y]).isroot || v.direction == r) {
                    if (v.id == this.active_node.id) continue;
                    if (!u.is_visible(v)) continue;
                    if (t = v.get_size(), e = v.get_location(), r == jsMind.direction.right) {
                        if (d - e.x - t.w <= 0) continue;
                        f = Math.abs(d - e.x - t.w) + Math.abs(c + l / 2 - e.y - t.h / 2), j = {
                            x: e.x + t.w - s,
                            y: e.y + t.h / 2
                        }, x = {x: d + s, y: c + l / 2}
                    } else {
                        if (e.x - d - a <= 0) continue;
                        f = Math.abs(d + a - e.x) + Math.abs(c + l / 2 - e.y - t.h / 2), j = {
                            x: e.x + s,
                            y: e.y + t.h / 2
                        }, x = {x: d + a - s, y: c + l / 2}
                    }
                    f < w && (m = v, g = j, p = x, w = f)
                }
            }
            var k = null;
            return m && (k = {node: m, direction: r, sp: p, np: g}), k
        }

        lookup_close_node() {
            var t = this._lookup_close_node();
            t && (this._magnet_shadow(t), this.target_node = t.node, this.target_direct = t.direction)
        }

        _event_bind() {
            var t = this, i = this.jm.view.container;
            e.on(i, "mousedown", (function (e) {
                var i = e || event;
                t.dragstart.call(t, i)
            })), e.on(i, "mousemove", (function (e) {
                var i = e || event;
                t.drag.call(t, i)
            })), e.on(i, "mouseup", (function (e) {
                var i = e || event;
                t.dragend.call(t, i)
            })), e.on(i, "touchstart", (function (e) {
                var i = e || event;
                t.dragstart.call(t, i)
            })), e.on(i, "touchmove", (function (e) {
                var i = e || event;
                t.drag.call(t, i)
            })), e.on(i, "touchend", (function (e) {
                var i = e || event;
                t.dragend.call(t, i)
            }))
        }

        dragstart(t) {
            if (this.jm.get_editable() && !this.capture) {
                this.active_node = null;
                var i = this.jm.view, s = t.target || event.srcElement;
                if ("jmnode" == s.tagName.toLowerCase()) {
                    var h = i.get_binded_nodeid(s);
                    if (h) {
                        var a = this.jm.get_node(h);
                        if (!a.isroot) {
                            this.reset_shadow(s), this.active_node = a, this.offset_x = (t.clientX || t.touches[0].clientX) / i.actualZoom - s.offsetLeft, this.offset_y = (t.clientY || t.touches[0].clientY) / i.actualZoom - s.offsetTop, this.client_hw = Math.floor(s.clientWidth / 2), this.client_hh = Math.floor(s.clientHeight / 2), 0 != this.hlookup_delay && e.w.clearTimeout(this.hlookup_delay), 0 != this.hlookup_timer && e.w.clearInterval(this.hlookup_timer);
                            var l = this;
                            this.hlookup_delay = e.w.setTimeout((function () {
                                l.hlookup_delay = 0, l.hlookup_timer = e.w.setInterval((function () {
                                    l.lookup_close_node.call(l)
                                }), n)
                            }), o), this.capture = !0

                        }
                    }
                }
            }
        }

        drag(t) {
            if (this.jm.get_editable() && this.capture) {
                t.preventDefault(), this.show_shadow(), this.moved = !0, i();
                var e = this.jm.view, s = (t.clientX || t.touches[0].clientX) / e.actualZoom - this.offset_x,
                    o = (t.clientY || t.touches[0].clientY) / e.actualZoom - this.offset_y;
                this.shadow.style.left = s + "px", this.shadow.style.top = o + "px", i()
            }
        }

        dragend(t) {
            if (this.jm.get_editable()) {
                if (this.capture) {
                    if (0 != this.hlookup_delay && (e.w.clearTimeout(this.hlookup_delay), this.hlookup_delay = 0, this._clear_lines()), 0 != this.hlookup_timer && (e.w.clearInterval(this.hlookup_timer), this.hlookup_timer = 0, this._clear_lines()), this.moved) {
                        var i = this.active_node, s = this.target_node, o = this.target_direct;
                        this.move_node(i, s, o)
                    }
                    this.hide_shadow()
                }
                this.moved = !1, this.capture = !1
                if (typeof(i) != "undefined") {
                    $.ajax({
                        type: "post",
                        url: "/mind/editIndex",
                        data: {
                            "id" : i.id,
                            "parentId" : i.parent.id,
                            "index" : i.index
                        },
                        success: function(r) {
                            if (r.msg == "未登录或登录超时。请重新登录") {
                                alert("未登录或登录超时。请重新登录");
                                location.reload();
                            }
                        }
                    });
                }

            }
        }

        move_node(t, e, i) {
            var s = this.shadow.offsetTop;
            if (e && t && !jsMind.node.inherited(t, e)) {
                for (var o = e.children, n = o.length, h = null, a = Number.MAX_VALUE, l = null, d = "_last_"; n--;) if ((h = o[n]).direction == i && h.id != t.id) {
                    var c = h.get_location().y - s;
                    c > 0 && c < a && (a = c, l = h, d = "_first_")
                }
                l && (d = l.id), this.jm.move_node(t.id, d, e.id, i)
            }
            this.active_node = null, this.target_node = null, this.target_direct = null
        }

        jm_event_handle(t, e) {
            t === jsMind.event_type.resize && this.resize()
        }
    }

    var a = new t.plugin("draggable", (function (t) {
        var e = new h(t);
        e.init(), t.add_event_listener((function (t, i) {
            e.jm_event_handle.call(e, t, i)
        }))
    }));
    jsMind.register_plugin(a)
}();
//# sourceMappingURL=jsmind.draggable.js.map
