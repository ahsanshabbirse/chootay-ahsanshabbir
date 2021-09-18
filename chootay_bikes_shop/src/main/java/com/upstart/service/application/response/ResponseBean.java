package com.upstart.service.application.response;

import org.springframework.stereotype.Component;

@Component
public class ResponseBean
{
	private String responseMessage;
	private Integer responseCode;
	private Data data;
	private PageInfoBean pageInfo;

	public String getResponseMessage()
	{
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage)
	{
		this.responseMessage = responseMessage;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public Data getData()
	{
		return data;
	}

	public void setData(Data data)
	{
		this.data = data;
	}

	public PageInfoBean getPageInfo()
	{
		return pageInfo;
	}

	public void setPageInfo(PageInfoBean pageInfo)
	{
		this.pageInfo = pageInfo;
	}

	@Override
	public String toString()
	{
		return "ResponseBean [responseMessage=" + responseMessage + ", responseCode=" + responseCode + ", data=" + data + ", pageInfo=" + pageInfo + "]";
	}

}
