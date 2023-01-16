package com.jetty.tfg;

import java.io.IOException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class HelloHandler extends AbstractHandler {

    final String _greeting;

    final String _body;

    public HelloHandler() {
        _greeting = "Hello World";
        _body = null;
    }

    public HelloHandler(String greeting) {
        _greeting = greeting;
        _body = null;
    }

    public HelloHandler(String greeting, String body) {
        _greeting = greeting;
        _body = body;
    }



	@Override
	public void handle(String target, Request baseRequest, jakarta.servlet.http.HttpServletRequest request,
			jakarta.servlet.http.HttpServletResponse response) throws IOException, jakarta.servlet.ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(200);
        baseRequest.setHandled(true);
        response.getWriter().println("<h1>" + _greeting + "</h1>");
        if (_body != null) response.getWriter().println(_body);

	}
}
