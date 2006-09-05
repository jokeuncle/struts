/*
 * $Id: PlainTextResult.java 394468 2006-04-16 12:16:03Z tmjee $
 *
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.struts2.jsf;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.struts2.dispatcher.StrutsResultSupport;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

/**
 * Executes the JSF render phase
 */
public class FacesResult extends StrutsResultSupport implements Result {

    private static final long serialVersionUID = -3548970638740937804L;

    /**
     * Checks to see if we need to build a new JSF ViewId from the Struts Result
     * config and then renders the result by delegating to the
     * FacesRender.render().
     * 
     * @see org.apache.struts2.dispatcher.StrutsResultSupport#doExecute(java.lang.String,
     *      com.opensymphony.
     */
    protected void doExecute(String finalLocation, ActionInvocation invocation)
            throws Exception {
        performNavigation(finalLocation, FacesContext.getCurrentInstance());
        new FacesRender().render(FacesContext.getCurrentInstance());
    }

    /**
     * Compares the Struts Result uri to the faces viewId. If they are different
     * use the Struts uri to build a new faces viewId.
     * 
     * @param finalLocation
     *            The result uri
     * @param facesContext
     *            The FacesContext
     */
    private void performNavigation(String finalLocation,
            FacesContext facesContext) {
        String facesViewId = facesContext.getViewRoot().getViewId();
        if (finalLocation != null) {
            if (finalLocation.equals(facesViewId) == false) {
                ViewHandler viewHandler = facesContext.getApplication()
                        .getViewHandler();
                UIViewRoot viewRoot = viewHandler.createView(facesContext,
                        finalLocation);
                facesContext.setViewRoot(viewRoot);
                facesContext.renderResponse();
            }
        }
    }

}
