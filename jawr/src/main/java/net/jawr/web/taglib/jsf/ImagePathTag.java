/**
 * Copyright 2009-2012 Ibrahim Chaehoi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 * 
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package net.jawr.web.taglib.jsf;

import java.io.IOException;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jawr.web.JawrConstant;
import net.jawr.web.resource.ImageResourcesHandler;
import net.jawr.web.servlet.RendererRequestUtils;
import net.jawr.web.taglib.ImageTagUtils;

/**
 * This class defines the JSF image path tag.
 * 
 * @author Ibrahim Chaehoi
 * 
 */
public class ImagePathTag extends UIOutput {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponentBase#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {

		// Initialize attributes
		String src = (String) getAttributes().get("src");

		// src is mandatory
		if (null == src)
			throw new IllegalStateException(
					"The src attribute is mandatory for the Jawr image path tag. ");

		HttpServletRequest request = ((HttpServletRequest)context.getExternalContext().getRequest());
		ImageResourcesHandler imgRsHandler = getImgResourcesHandler(context);
		
		//Refresh the config if needed
        RendererRequestUtils.refreshConfigIfNeeded(request, imgRsHandler.getJawrConfig());
        
		render(context);

		super.encodeBegin(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.jawr.web.taglib.jsf.AbstractImageTag#render(javax.faces.context.FacesContext)
	 */
	protected void render(FacesContext context) throws IOException {

		String src = (String) getAttributes().get("src");
		boolean base64 = Boolean.valueOf((String) getAttributes().get("src")).booleanValue();
		String imagePath = getImageUrl(context, src, base64);
		ResponseWriter writer = context.getResponseWriter();
		writer.write(imagePath);
	}

	/**
	 * Returns the image URL generated by Jawr from a source image path
	 * @param context the faces context
	 * @param src the image source
	 * @param base64 the flag indicating if the image should be encoded in base64 
	 * @return the image URL generated by Jawr from a source image path
	 */
	protected String getImageUrl(FacesContext context, String src, boolean base64) {
		ImageResourcesHandler imgRsHandler = getImgResourcesHandler(context);

		HttpServletResponse response = ((HttpServletResponse) context
				.getExternalContext().getResponse());
		
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		
		return ImageTagUtils.getImageUrl(src, base64, imgRsHandler, request,
				response);
	}

	/**
	 * Returns the image resource handler
	 * @param context the faces context
	 * @return the image resource handler
	 */
	protected ImageResourcesHandler getImgResourcesHandler(FacesContext context) {
	
		// Generate the path for the image element
		ImageResourcesHandler imgRsHandler = (ImageResourcesHandler) context
				.getExternalContext().getApplicationMap().get(
						JawrConstant.IMG_CONTEXT_ATTRIBUTE);
		if (imgRsHandler == null) {
			throw new IllegalStateException(
					"You are using a Jawr image tag while the Jawr Image servlet has not been initialized. Initialization of Jawr Image servlet either failed or never occurred.");
		}
		return imgRsHandler;
	}
}
