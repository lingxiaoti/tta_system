/**
 * Created by Administrator on 2018/4/19.
 * API   http://www.bootcdn.cn/angular-websocket/readme/
 */
!function (e, t) {
    if ("function" == typeof define && define.amd)define(["module", "exports", "angular", "ws"], t); else if ("undefined" != typeof exports)t(module, exports, require("angular"), require("ws")); else {
        var o = {exports: {}};
        t(o, o.exports, e.angular, e.ws), e.angularWebsocket = o.exports
    }
}(this, function (e, t, o, n) {
    "use strict";
    function s(e) {
        return e && e.__esModule ? e : {"default": e}
    }

    function r(e, t, o, n) {
        function s(t, o, n) {
            n || !k(o) || C(o) || (n = o, o = void 0), this.protocols = o, this.url = t || "Missing URL", this.ssl = /(wss)/i.test(this.url), this.scope = n && n.scope || e, this.rootScopeFailover = n && n.rootScopeFailover && !0, this.useApplyAsync = n && n.useApplyAsync || !1, this.initialTimeout = n && n.initialTimeout || 500, this.maxTimeout = n && n.maxTimeout || 3e5, this.reconnectIfNotNormalClose = n && n.reconnectIfNotNormalClose || !1, this.binaryType = n && n.binaryType || "blob", this._reconnectAttempts = 0, this.sendQueue = [], this.onOpenCallbacks = [], this.onMessageCallbacks = [], this.onErrorCallbacks = [], this.onCloseCallbacks = [], f(this._readyStateConstants), t ? this._connect() : this._setInternalState(0)
        }

        return s.prototype._readyStateConstants = {
            CONNECTING: 0,
            OPEN: 1,
            CLOSING: 2,
            CLOSED: 3,
            RECONNECT_ABORTED: 4
        }, s.prototype._normalCloseCode = 1e3, s.prototype._reconnectableStatusCodes = [4e3], s.prototype.safeDigest = function (e) {
            e && !this.scope.$$phase && this.scope.$digest()
        }, s.prototype.bindToScope = function (t) {
            var o = this;
            return t && (this.scope = t, this.rootScopeFailover && this.scope.$on("$destroy", function () {
                o.scope = e
            })), o
        }, s.prototype._connect = function (e) {
            !e && this.socket && this.socket.readyState === this._readyStateConstants.OPEN || (this.socket = n.create(this.url, this.protocols), this.socket.onmessage = c["default"].bind(this, this._onMessageHandler), this.socket.onopen = c["default"].bind(this, this._onOpenHandler), this.socket.onerror = c["default"].bind(this, this._onErrorHandler), this.socket.onclose = c["default"].bind(this, this._onCloseHandler), this.socket.binaryType = this.binaryType)
        }, s.prototype.fireQueue = function () {
            for (; this.sendQueue.length && this.socket.readyState === this._readyStateConstants.OPEN;) {
                var e = this.sendQueue.shift();
                this.socket.send(d(e.message) || "blob" != this.binaryType ? e.message : JSON.stringify(e.message)), e.deferred.resolve()
            }
        }, s.prototype.notifyOpenCallbacks = function (e) {
            for (var t = 0; t < this.onOpenCallbacks.length; t++)this.onOpenCallbacks[t].call(this, e)
        }, s.prototype.notifyCloseCallbacks = function (e) {
            for (var t = 0; t < this.onCloseCallbacks.length; t++)this.onCloseCallbacks[t].call(this, e)
        }, s.prototype.notifyErrorCallbacks = function (e) {
            for (var t = 0; t < this.onErrorCallbacks.length; t++)this.onErrorCallbacks[t].call(this, e)
        }, s.prototype.onOpen = function (e) {
            return this.onOpenCallbacks.push(e), this
        }, s.prototype.onClose = function (e) {
            return this.onCloseCallbacks.push(e), this
        }, s.prototype.onError = function (e) {
            return this.onErrorCallbacks.push(e), this
        }, s.prototype.onMessage = function (e, t) {
            if (!y(e))throw new Error("Callback must be a function");
            if (t && b(t.filter) && !d(t.filter) && !(t.filter instanceof RegExp))throw new Error("Pattern must be a string or regular expression");
            return this.onMessageCallbacks.push({
                fn: e,
                pattern: t ? t.filter : void 0,
                autoApply: t ? t.autoApply : !0
            }), this
        }, s.prototype._onOpenHandler = function (e) {
            this._reconnectAttempts = 0, this.notifyOpenCallbacks(e), this.fireQueue()
        }, s.prototype._onCloseHandler = function (e) {
            var t = this;
            t.useApplyAsync ? t.scope.$applyAsync(function () {
                t.notifyCloseCallbacks(e)
            }) : (t.notifyCloseCallbacks(e), t.safeDigest(!0)), (this.reconnectIfNotNormalClose && e.code !== this._normalCloseCode || this._reconnectableStatusCodes.indexOf(e.code) > -1) && this.reconnect()
        }, s.prototype._onErrorHandler = function (e) {
            var t = this;
            t.useApplyAsync ? t.scope.$applyAsync(function () {
                t.notifyErrorCallbacks(e)
            }) : (t.notifyErrorCallbacks(e), t.safeDigest(!0))
        }, s.prototype._onMessageHandler = function (e) {
            function t(e, t, o) {
                o = m.call(arguments, 2), s.useApplyAsync ? s.scope.$applyAsync(function () {
                    e.apply(s, o)
                }) : (e.apply(s, o), s.safeDigest(t))
            }

            for (var o, n, s = this, r = 0; r < s.onMessageCallbacks.length; r++)n = s.onMessageCallbacks[r], o = n.pattern, o ? d(o) && e.data === o ? t(n.fn, n.autoApply, e) : o instanceof RegExp && o.exec(e.data) && t(n.fn, n.autoApply, e) : t(n.fn, n.autoApply, e)
        }, s.prototype.close = function (e) {
            return !e && this.socket.bufferedAmount || this.socket.close(), this
        }, s.prototype.send = function (e) {
            function o(e) {
                e.cancel = s;
                var t = e.then;
                return e.then = function () {
                    var e = t.apply(this, arguments);
                    return o(e)
                }, e
            }

            function s(t) {
                return i.sendQueue.splice(i.sendQueue.indexOf(e), 1), r.reject(t), i
            }

            var r = t.defer(), i = this, a = o(r.promise);
            return i.readyState === i._readyStateConstants.RECONNECT_ABORTED ? r.reject("Socket connection has been closed") : (i.sendQueue.push({
                message: e,
                deferred: r
            }), i.fireQueue()), n.isMocked && n.isMocked() && n.isConnected(this.url) && this._onMessageHandler(n.mockSend()), a
        }, s.prototype.reconnect = function () {
            this.close();
            var e = this._getBackoffDelay(++this._reconnectAttempts), t = e / 1e3;
            return console.log("Reconnecting in " + t + " seconds"), o(c["default"].bind(this, this._connect), e), this
        }, s.prototype._getBackoffDelay = function (e) {
            var t = Math.random() + 1, o = this.initialTimeout, n = 2, s = e, r = this.maxTimeout;
            return Math.floor(Math.min(t * o * Math.pow(n, s), r))
        }, s.prototype._setInternalState = function (e) {
            if (Math.floor(e) !== e || 0 > e || e > 4)throw new Error("state must be an integer between 0 and 4, got: " + e);
            h || (this.readyState = e || this.socket.readyState), this._internalConnectionState = e, g(this.sendQueue, function (e) {
                e.deferred.reject("Message cancelled due to closed socket connection")
            })
        }, h && h(s.prototype, "readyState", {
            get: function () {
                return this._internalConnectionState || this.socket.readyState
            }, set: function () {
                throw new Error("The readyState property is read-only")
            }
        }), function (e, t, o) {
            return new s(e, t, o)
        }
    }

    function i(e) {
        this.create = function (e, t) {
            var o = /wss?:\/\//.exec(e);
            if (!o)throw new Error("Invalid url provided");
            return t ? new a(e, t) : new a(e)
        }, this.createWebSocketBackend = function (t, o) {
            return e.warn("Deprecated: Please use .create(url, protocols)"), this.create(t, o)
        }
    }

    Object.defineProperty(t, "__esModule", {value: !0});
    var a, c = s(o), l = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function (e) {
        return typeof e
    } : function (e) {
        return e && "function" == typeof Symbol && e.constructor === Symbol ? "symbol" : typeof e
    };
    if ("object" === ("undefined" == typeof t ? "undefined" : l(t)) && "function" == typeof require)try {
        a = n.Client || n.client || n
    } catch (u) {
    }
    a = a || window.WebSocket || window.MozWebSocket;
    var p = c["default"].noop, f = Object.freeze ? Object.freeze : p, h = Object.defineProperty, d = c["default"].isString, y = c["default"].isFunction, b = c["default"].isDefined, k = c["default"].isObject, C = c["default"].isArray, g = c["default"].forEach, m = Array.prototype.slice;
    Array.prototype.indexOf || (Array.prototype.indexOf = function (e) {
        var t = this.length >>> 0, o = Number(arguments[1]) || 0;
        for (o = 0 > o ? Math.ceil(o) : Math.floor(o), 0 > o && (o += t); t > o; o++)if (o in this && this[o] === e)return o;
        return -1
    }), c["default"].module("ngWebSocket", []).factory("$websocket", ["$rootScope", "$q", "$timeout", "$websocketBackend", r]).factory("WebSocket", ["$rootScope", "$q", "$timeout", "WebsocketBackend", r]).service("$websocketBackend", ["$log", i]).service("WebSocketBackend", ["$log", i]), c["default"].module("angular-websocket", ["ngWebSocket"]), t["default"] = c["default"].module("ngWebSocket"), e.exports = t["default"]
});
//# sourceMappingURL=angular-websocket.min.js.map