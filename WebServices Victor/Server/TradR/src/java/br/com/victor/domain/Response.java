/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.victor.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author victo
 */
@XmlRootElement
public class Response {
    private String status;
	private String msg;

	public Response() {
	}

	public static Response Ok(String string) {
		Response r = new Response();
		r.setStatus("OK");
		r.setMsg(string);
		return r;
	}

	public static Response Error(String string) {
		Response r = new Response();
		r.setStatus("ERROR");
		r.setMsg(string);
		return r;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
