// Automatically generated - do not modify!

package web.html

import web.dom.Node
import web.events.Event
import web.events.EventInstance
import web.uievents.*

inline val <C : HTMLSelectElement> C.changeEvent: EventInstance<Event, C, Node>
    get() = EventInstance(this, Event.change())

inline val <C : HTMLSelectElement> C.invalidEvent: EventInstance<Event, C, C>
    get() = EventInstance(this, Event.invalid())
