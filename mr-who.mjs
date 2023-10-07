function G(e, t, n, ...l) {
  if (l.length % 2 !== 0)
    throw new Error("Illegal argument: assoc expects an odd number of arguments.");
  switch (b(e)) {
    case m:
      e.set(t, n);
      for (let r = 0; r < l.length; r += 2)
        e.set(l[r], l[r + 1]);
      break;
    case y:
    case w:
      e[t] = n;
      for (let r = 0; r < l.length; r += 2)
        e[l[r]] = l[r + 1];
      break;
    default:
      throw new Error(
        "Illegal argument: assoc! expects a Map, Array, or Object as the first argument."
      );
  }
  return e;
}
function le(e, t, n, ...l) {
  switch (e || (e = {}), b(e)) {
    case m:
      return G(new Map(e.entries()), t, n, ...l);
    case y:
      return G([...e], t, n, ...l);
    case w:
      return G({ ...e }, t, n, ...l);
    default:
      throw new Error(
        "Illegal argument: assoc expects a Map, Array, or Object as the first argument."
      );
  }
}
const m = 1, y = 2, w = 3, Q = 4, x = 5, L = 6;
function ae(e) {
  switch (e) {
    case m:
      return /* @__PURE__ */ new Map();
    case y:
      return [];
    case w:
      return {};
    case Q:
      return new P();
    case x:
      return /* @__PURE__ */ new Set();
    case L:
      return j(function* () {
      });
  }
}
function b(e) {
  if (e instanceof Map)
    return m;
  if (e instanceof Set)
    return x;
  if (e instanceof P)
    return Q;
  if (e instanceof Array)
    return y;
  if (e instanceof C)
    return L;
  if (e instanceof Object)
    return w;
}
function ue(e, t, n, l, r) {
  let a = b(n);
  if (a !== m && a !== y && a !== w)
    throw new Error(
      `Illegal argument: ${t} expects the first argument to be a Map, Array, or Object.`
    );
  const _ = [n];
  let u = n;
  for (let i = 0; i < l.length - 1; i += 1) {
    let s = l[i], o;
    u instanceof Map ? o = u.get(s) : o = u[s], o || (o = ae(a)), _.push(o), u = o;
  }
  _.push(r);
  for (let i = _.length - 2; i >= 0; i -= 1)
    _[i] = e(_[i], l[i], _[i + 1]);
  return _[0];
}
function V(e, t, n) {
  return ue(le, "assoc-in", e, t, n);
}
function T(...e) {
  if (e.length === 0)
    return pe();
  let [t, ...n] = e;
  switch (t == null && (t = []), b(t)) {
    case x:
      return /* @__PURE__ */ new Set([...t, ...n]);
    case Q:
      return new P(...n.reverse(), ...t);
    case y:
      return [...t, ...n];
    case m:
      const l = new Map(t);
      for (const a of n)
        a instanceof Array ? l.set(a[0], a[1]) : f(a).forEach((_) => {
          l.set(_[0], _[1]);
        });
      return l;
    case L:
      return j(function* () {
        yield* n, yield* t;
      });
    case w:
      const r = { ...t };
      for (const a of n)
        a instanceof Array ? r[a[0]] = a[1] : Object.assign(r, a);
      return r;
    default:
      throw new Error(
        "Illegal argument: conj expects a Set, Array, List, Map, or Object as the first argument."
      );
  }
}
function h(e, t, n) {
  if (e) {
    var l = void 0;
    if (e instanceof Array)
      l = e[t];
    else {
      let r = f(e), a = 0;
      for (let _ of r)
        if (a++ == t) {
          l = _;
          break;
        }
    }
    if (l !== void 0)
      return l;
  }
  return n;
}
function M(e, t, n = void 0) {
  let l;
  switch (b(e)) {
    case x:
      e.has(t) && (l = t);
      break;
    case m:
      l = e.get(t);
      break;
    case void 0:
      break;
    default:
      l = e[t];
      break;
  }
  return l !== void 0 ? l : n;
}
function ie(e) {
  return typeof e == "string" || e === null || e === void 0 || Symbol.iterator in e;
}
function f(e) {
  return e == null ? [] : ie(e) ? e : Object.entries(e);
}
const se = Symbol("Iterable");
function oe(e) {
  return e[Symbol.iterator]();
}
const ce = oe;
function $(e) {
  let t = f(e);
  return t.length === 0 || t.size === 0 ? null : t;
}
function E(e) {
  let [t] = f(e);
  return t;
}
function z(e) {
  let [t, n] = f(e);
  return n;
}
function k(e) {
  return j(function* () {
    let t = !0;
    for (const n of f(e))
      t ? t = !1 : yield n;
  });
}
function fe(e) {
  switch (e = f(e), b(e)) {
    case y:
      return e[e.length - 1];
    default:
      let t;
      for (const n of e)
        t = n;
      return t;
  }
}
class C {
  constructor(t) {
    this.gen = t;
  }
  [Symbol.iterator]() {
    return this.gen();
  }
}
C.prototype[se] = !0;
function j(e) {
  return new C(e);
}
function D(e, ...t) {
  switch (t.length) {
    case 0:
      throw new Error("map with 2 arguments is not supported yet");
    case 1:
      return j(function* () {
        for (const n of f(t[0]))
          yield e(n);
      });
    default:
      return j(function* () {
        const n = t.map((l) => ce(f(l)));
        for (; ; ) {
          let l = [];
          for (const r of n) {
            const a = r.next();
            if (a.done)
              return;
            l.push(a.value);
          }
          yield e(...l);
        }
      });
  }
}
function $e(e, t) {
  return j(function* () {
    for (const n of f(t))
      e(n) && (yield n);
  });
}
function ge(e, t) {
  return $e(ye(e), t);
}
function O(...e) {
  return e.join("");
}
function de(e) {
  return !e;
}
function J(e) {
  return e._deref();
}
function ve(e, t) {
  e._reset_BANG_(t);
}
function U(e, t, ...n) {
  const l = t(J(e), ...n);
  return ve(e, l), l;
}
function pe(...e) {
  return e;
}
function he(...e) {
  return [...D(...e)];
}
function me(e) {
  return [...f(e)];
}
function ye(e) {
  return (...t) => de(e(...t));
}
class P extends Array {
  constructor(...t) {
    super(), this.push(...t);
  }
}
function d(...e) {
  switch (e.length) {
    case 0:
      return [];
    case 1:
      return e[0];
    default:
      return T(e[0] ?? [], ...f(e[1]));
  }
}
function Z(e, t, n) {
  let l = e;
  for (const r of t)
    l = M(l, r);
  return l === void 0 ? n : l;
}
function je(e, t, n, ...l) {
  return V(e, t, n(Z(e, t), ...l));
}
function H(e) {
  return !$(e);
}
function we(e) {
  return e.length;
}
function be(e) {
  return e instanceof Object;
}
var Ie = function() {
  let e = function(t) {
    let n = [], l = arguments.length, r = 0;
    for (; ; ) {
      let _ = r < l;
      if (_ != null && _ !== !1) {
        n.push(arguments[r]), r = r + 1;
        continue;
      }
      break;
    }
    let a = function() {
      let _ = 0 < n.length;
      if (_ != null && _ !== !1)
        return n.slice(0);
    }();
    return e.cljs$core$IFn$_invoke$arity$variadic(a);
  };
  return e.cljs$core$IFn$_invoke$arity$variadic = function(t) {
    return d(["div"], t);
  }, e.cljs$lang$maxFixedArity = 0, e.cljs$lang$applyTo = function(t) {
    return this.cljs$core$IFn$_invoke$arity$variadic($(t));
  }, e;
}(), Fe = function() {
  let e = function(t) {
    let n = [], l = arguments.length, r = 0;
    for (; ; ) {
      let _ = r < l;
      if (_ != null && _ !== !1) {
        n.push(arguments[r]), r = r + 1;
        continue;
      }
      break;
    }
    let a = function() {
      let _ = 0 < n.length;
      if (_ != null && _ !== !1)
        return n.slice(0);
    }();
    return e.cljs$core$IFn$_invoke$arity$variadic(a);
  };
  return e.cljs$core$IFn$_invoke$arity$variadic = function(t) {
    return d(["button"], t);
  }, e.cljs$lang$maxFixedArity = 0, e.cljs$lang$applyTo = function(t) {
    return this.cljs$core$IFn$_invoke$arity$variadic($(t));
  }, e;
}(), ke = function() {
  let e = function(t) {
    let n = [], l = arguments.length, r = 0;
    for (; ; ) {
      let _ = r < l;
      if (_ != null && _ !== !1) {
        n.push(arguments[r]), r = r + 1;
        continue;
      }
      break;
    }
    let a = function() {
      let _ = 0 < n.length;
      if (_ != null && _ !== !1)
        return n.slice(0);
    }();
    return e.cljs$core$IFn$_invoke$arity$variadic(a);
  };
  return e.cljs$core$IFn$_invoke$arity$variadic = function(t) {
    return d(["span"], t);
  }, e.cljs$lang$maxFixedArity = 0, e.cljs$lang$applyTo = function(t) {
    return this.cljs$core$IFn$_invoke$arity$variadic($(t));
  }, e;
}(), Ae = function() {
  let e = function(t) {
    let n = [], l = arguments.length, r = 0;
    for (; ; ) {
      let _ = r < l;
      if (_ != null && _ !== !1) {
        n.push(arguments[r]), r = r + 1;
        continue;
      }
      break;
    }
    let a = function() {
      let _ = 0 < n.length;
      if (_ != null && _ !== !1)
        return n.slice(0);
    }();
    return e.cljs$core$IFn$_invoke$arity$variadic(a);
  };
  return e.cljs$core$IFn$_invoke$arity$variadic = function(t) {
    return d(["a"], t);
  }, e.cljs$lang$maxFixedArity = 0, e.cljs$lang$applyTo = function(t) {
    return this.cljs$core$IFn$_invoke$arity$variadic($(t));
  }, e;
}(), xe = function() {
  let e = function(t) {
    let n = [], l = arguments.length, r = 0;
    for (; ; ) {
      let _ = r < l;
      if (_ != null && _ !== !1) {
        n.push(arguments[r]), r = r + 1;
        continue;
      }
      break;
    }
    let a = function() {
      let _ = 0 < n.length;
      if (_ != null && _ !== !1)
        return n.slice(0);
    }();
    return e.cljs$core$IFn$_invoke$arity$variadic(a);
  };
  return e.cljs$core$IFn$_invoke$arity$variadic = function(t) {
    return d(["nav"], t);
  }, e.cljs$lang$maxFixedArity = 0, e.cljs$lang$applyTo = function(t) {
    return this.cljs$core$IFn$_invoke$arity$variadic($(t));
  }, e;
}(), Ge = function() {
  let e = function(t) {
    let n = [], l = arguments.length, r = 0;
    for (; ; ) {
      let _ = r < l;
      if (_ != null && _ !== !1) {
        n.push(arguments[r]), r = r + 1;
        continue;
      }
      break;
    }
    let a = function() {
      let _ = 0 < n.length;
      if (_ != null && _ !== !1)
        return n.slice(0);
    }();
    return e.cljs$core$IFn$_invoke$arity$variadic(a);
  };
  return e.cljs$core$IFn$_invoke$arity$variadic = function(t) {
    return d(["header"], t);
  }, e.cljs$lang$maxFixedArity = 0, e.cljs$lang$applyTo = function(t) {
    return this.cljs$core$IFn$_invoke$arity$variadic($(t));
  }, e;
}(), Te = function() {
  let e = function(t) {
    let n = [], l = arguments.length, r = 0;
    for (; ; ) {
      let _ = r < l;
      if (_ != null && _ !== !1) {
        n.push(arguments[r]), r = r + 1;
        continue;
      }
      break;
    }
    let a = function() {
      let _ = 0 < n.length;
      if (_ != null && _ !== !1)
        return n.slice(0);
    }();
    return e.cljs$core$IFn$_invoke$arity$variadic(a);
  };
  return e.cljs$core$IFn$_invoke$arity$variadic = function(t) {
    return d(["svg"], t);
  }, e.cljs$lang$maxFixedArity = 0, e.cljs$lang$applyTo = function(t) {
    return this.cljs$core$IFn$_invoke$arity$variadic($(t));
  }, e;
}(), Me = function() {
  let e = function(t) {
    let n = [], l = arguments.length, r = 0;
    for (; ; ) {
      let _ = r < l;
      if (_ != null && _ !== !1) {
        n.push(arguments[r]), r = r + 1;
        continue;
      }
      break;
    }
    let a = function() {
      let _ = 0 < n.length;
      if (_ != null && _ !== !1)
        return n.slice(0);
    }();
    return e.cljs$core$IFn$_invoke$arity$variadic(a);
  };
  return e.cljs$core$IFn$_invoke$arity$variadic = function(t) {
    return d(["path"], t);
  }, e.cljs$lang$maxFixedArity = 0, e.cljs$lang$applyTo = function(t) {
    return this.cljs$core$IFn$_invoke$arity$variadic($(t));
  }, e;
}(), Ee = function() {
  let e = function(t) {
    let n = [], l = arguments.length, r = 0;
    for (; ; ) {
      let _ = r < l;
      if (_ != null && _ !== !1) {
        n.push(arguments[r]), r = r + 1;
        continue;
      }
      break;
    }
    let a = function() {
      let _ = 0 < n.length;
      if (_ != null && _ !== !1)
        return n.slice(0);
    }();
    return e.cljs$core$IFn$_invoke$arity$variadic(a);
  };
  return e.cljs$core$IFn$_invoke$arity$variadic = function(t) {
    return d(["ul"], t);
  }, e.cljs$lang$maxFixedArity = 0, e.cljs$lang$applyTo = function(t) {
    return this.cljs$core$IFn$_invoke$arity$variadic($(t));
  }, e;
}(), Oe = function() {
  let e = function(t) {
    let n = [], l = arguments.length, r = 0;
    for (; ; ) {
      let _ = r < l;
      if (_ != null && _ !== !1) {
        n.push(arguments[r]), r = r + 1;
        continue;
      }
      break;
    }
    let a = function() {
      let _ = 0 < n.length;
      if (_ != null && _ !== !1)
        return n.slice(0);
    }();
    return e.cljs$core$IFn$_invoke$arity$variadic(a);
  };
  return e.cljs$core$IFn$_invoke$arity$variadic = function(t) {
    return d(["li"], t);
  }, e.cljs$lang$maxFixedArity = 0, e.cljs$lang$applyTo = function(t) {
    return this.cljs$core$IFn$_invoke$arity$variadic($(t));
  }, e;
}(), Se = function() {
  let e = function(t) {
    let n = [], l = arguments.length, r = 0;
    for (; ; ) {
      let _ = r < l;
      if (_ != null && _ !== !1) {
        n.push(arguments[r]), r = r + 1;
        continue;
      }
      break;
    }
    let a = function() {
      let _ = 0 < n.length;
      if (_ != null && _ !== !1)
        return n.slice(0);
    }();
    return e.cljs$core$IFn$_invoke$arity$variadic(a);
  };
  return e.cljs$core$IFn$_invoke$arity$variadic = function(t) {
    return d(["img"], t);
  }, e.cljs$lang$maxFixedArity = 0, e.cljs$lang$applyTo = function(t) {
    return this.cljs$core$IFn$_invoke$arity$variadic($(t));
  }, e;
}(), qe = function(e, t) {
  return t.parentNode.replaceChild(e, t);
}, Re = function(e, t) {
  return t.parent.appendChild();
}, Ke = function(e) {
  return old.parentNode().removeChild()(e);
};
const Ce = /* @__PURE__ */ Object.freeze(/* @__PURE__ */ Object.defineProperty({
  __proto__: null,
  a: Ae,
  append_child: Re,
  button: Fe,
  div: Ie,
  header: Ge,
  img: Se,
  li: Oe,
  nav: xe,
  path: Me,
  remove_node: Ke,
  replace_node: qe,
  span: ke,
  svg: Te,
  ul: Ee
}, Symbol.toStringTag, { value: "Module" }));
function Qe(e) {
  return !e || e.length === 0 || e.trimLeft().length === 0;
}
function Le(e, t) {
  if (t === void 0 && (t = e, e = ""), t instanceof Array)
    return t.join(e);
  let n = "", l = !1;
  for (const r of f(t))
    l && (n += e), n += r, l = !0;
  return n;
}
function S(e, t) {
  return e.split(t);
}
var Y = function(e) {
  return typeof e == "string";
}, B = function(e) {
  return typeof e > "u";
}, W = function(e) {
  return typeof e == "boolean";
}, N = function(e) {
  return typeof e == "number";
}, A = function() {
  return crypto.randomUUID();
}, X = function(e, t) {
  return t.match(e);
}, q = function(e, t, n) {
  return e.replace(t, n);
}, R = function(e) {
  let t = e === "div";
  if (t != null && t !== !1)
    return t;
  {
    let n = e === "p";
    if (n != null && n !== !1)
      return n;
    {
      let l = e === "button";
      if (l != null && l !== !1)
        return l;
      {
        let r = e === "span";
        if (r != null && r !== !1)
          return r;
        {
          let a = e === "a";
          if (a != null && a !== !1)
            return a;
          {
            let _ = e === "nav";
            if (_ != null && _ !== !1)
              return _;
            {
              let u = e === "svg";
              if (u != null && u !== !1)
                return u;
              {
                let i = e === "path";
                if (i != null && i !== !1)
                  return i;
                {
                  let s = e === "ul";
                  if (s != null && s !== !1)
                    return s;
                  {
                    let o = e === "li";
                    if (o != null && o !== !1)
                      return o;
                    {
                      let c = e === "img";
                      return c != null && c !== !1 ? c : e === "header";
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
}, ee = function(e) {
  let t = Y(e);
  if (t != null && t !== !1)
    return t;
  {
    let n = N(e);
    if (n != null && n !== !1)
      return n;
    {
      let l = W(e);
      return l != null && l !== !1 ? l : B(e);
    }
  }
}, F = function() {
  let e = function(t) {
    switch (arguments.length) {
      case 4:
        return e.cljs$core$IFn$_invoke$arity$4(arguments[0], arguments[1], arguments[2], arguments[3]);
      case 5:
        return e.cljs$core$IFn$_invoke$arity$5(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]);
      default:
        throw new Error(O("Invalid arity: ", we(arguments)));
    }
  };
  return e.cljs$core$IFn$_invoke$arity$4 = function(t, n, l, r) {
    return F(t, n, l, r, []);
  }, e.cljs$core$IFn$_invoke$arity$5 = function(t, n, l, r, a) {
    return { "mr-who/id": t, element: n, type: l, attr: r, children: a };
  }, e.cljs$lang$maxFixedArity = 5, e;
}(), te = function(e) {
  let n = S(e, /\./), l = E(n), r = S(l, /#/), a = h(r, 0, null), _ = h(r, 1, null), u = k(n);
  return [a, _, u];
}, ne = function(e) {
  return Le("; ", D(function(t) {
    let n = t, l = h(n, 0, null), r = h(n, 1, null), a = l, _ = function() {
      let u = N(r);
      return u != null && u !== !1 ? O(r, "px") : r;
    }();
    return O(a, ": ", _);
  }, e));
}, K = function(e, t) {
  let n = ge(function(r) {
    let a = B(r);
    return a != null && a !== !1 ? a : Qe(r);
  }, t), l = H(n);
  if (l != null && l !== !1)
    return null;
  for (let r of n)
    e.classList.add(r);
  return null;
}, re = function(e, t) {
  let n = te(e), l = h(n, 0, null), r = h(n, 1, null), a = h(n, 2, null), _ = document.createElement(l);
  r != null && r !== !1 && (_.id = r), K(_, a);
  let u = H(t);
  if (!(u != null && u !== !1))
    for (let [i, s] of Object.entries(t))
      (function() {
        let o = i === "style";
        if (o != null && o !== !1)
          return _.setAttribute("style", ne(s));
        {
          let c = i === "class";
          if (c != null && c !== !1) {
            let v = function() {
              let g = Y(s);
              if (g != null && g !== !1)
                return S(s, /\s+/);
            }();
            return K(_, v);
          } else {
            let v = X(/on-\w+-*\w+/, i);
            if (v != null && v !== !1) {
              let g = q(i, "on-", ""), p = q(g, "-", "");
              return _.addEventListener(p, s);
            } else
              return _.setAttribute(i, s);
          }
        }
      })();
  return _;
}, I = function(e, t, n) {
  let l = n, r = M(l, "fun"), a = M(l, "app"), _ = ee(t);
  if (_ != null && _ !== !1) {
    let u = e.appendChild(document.createTextNode(t)), i = A();
    return r != null && r !== !1 && r(["mr-who/id", i]), U(a, V, ["mr-who/id", i], F(i, u, "text", {}));
  } else {
    let u = E(t), i = z(t), s = me(k(k(t))), o = function() {
      let c = R(u);
      return c != null && c !== !1 ? be(i) : c;
    }();
    if (o != null && o !== !1) {
      let c = e.appendChild(re(u, i));
      return F(A(), c, u, i, s != null && s !== !1 ? I(c, s, { app: a }) : []);
    } else {
      let c = u === "app-cursor";
      if (c != null && c !== !1) {
        let v = Z(J(a), i), g = [E(i), z(i)], p = fe(i);
        return I(e, v, { app: a, fun: function(_e) {
          return U(a, je, T(g, "mr-who/mounted-elements"), T, [p, _e]);
        } });
      } else {
        let v = R(u);
        if (v != null && v !== !1) {
          let g = e.appendChild(document.createElement(u)), p = k(t);
          return F(A(), g, u, {}, p != null && p !== !1 ? I(g, p, { app: a }) : []);
        } else
          return he(function(g) {
            return I(e, g, { app: a });
          }, t);
      }
    }
  }
};
const Pe = /* @__PURE__ */ Object.freeze(/* @__PURE__ */ Object.defineProperty({
  __proto__: null,
  add_css_to_element: K,
  boolean_QMARK_: W,
  create_element: re,
  create_vdom_element: F,
  extract_tag_id_css_classes: te,
  keyword_QMARK_: R,
  nil_QMARK_: B,
  number_QMARK_: N,
  primitive_QMARK_: ee,
  random_uuid: A,
  re_find: X,
  render_and_meta_things: I,
  replace: q,
  string_QMARK_: Y,
  style_map__GT_css_str: ne
}, Symbol.toStringTag, { value: "Module" }));
export {
  Ce as dom,
  Pe as render
};
