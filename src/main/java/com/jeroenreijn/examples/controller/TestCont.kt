package com.jeroenreijn.examples.controller

import com.github.jsocle.html.Node
import com.github.jsocle.html.elements.Div
import com.github.jsocle.html.elements.Html
import com.github.jsocle.html.elements.Link
import com.jeroenreijn.examples.model.Presentation
import java.io.OutputStream

public class SafeTextNode(private val text: String) : Node() {
    override fun render(builder: Appendable) {
        builder.append(text)
    }
}

val styles = Link(rel = "stylesheet", href = "/webjars/bootstrap/3.0.1/css/bootstrap.min.css", media = "screen")
val scripts = Div {
    script(src = "/webjars/jquery/2.0.2/jquery.min.js")
    script(src = "/webjars/bootstrap/3.0.1/js/bootstrap.min.js")
}

fun layout(title: String, doBody: Div.() -> Unit): Html {
    return Html {
        head {
            meta { attributes["charset"] = "utf-8/" }
            meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
            meta(httpEquiv = "X-UA-Compatible", content = "IE=Edge")
            title(text_ = title)
            +styles
        }
        body {
            div(class_ = "container") {
                div(class_ = "page-header") {
                    h1(text_ = title)
                }
                doBody()
            }
            +scripts
        }
    }
}

fun view(presentaion: Iterable<Presentation>): Html {
    return layout("JFall 2013 Presentations - JSocle") {
        presentaion.forEach {
            div(class_ = "panel panel-default") {
                div(class_ = "panel-heading") {
                    h3(class_ = "panel-title", text_ = "${it.getTitle()} - ${it.getSpeakerName()}")
                }
                div(class_ = "panel-body") { +SafeTextNode(it.getSummary()) }
            }
        }
    }
}
