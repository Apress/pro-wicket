/*
 * $Id: VelocityPanel.java,v 1.3 2005/03/31 20:50:42 joco01 Exp $ $Revision:
 * 1.1 $ $Date: 2005/03/31 20:50:42 $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.markup.html.velocity;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import wicket.WicketRuntimeException;
import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.WebComponent;
import wicket.model.Model;
import wicket.util.resource.IStringResourceStream;
import wicket.util.string.Strings;

/**
 * Panel that displays the result of rendering a Velocity template. The template
 * itself can be any IStringResourceStream implementation, of which there are a number
 * of convenient implementations in wicket.util. The model can be any normal
 * Wicket MapModel.
 * 
 * @author Eelco Hillenius
 * @author Jonathan Locke
 */
public final class VelocityPanel extends WebComponent
{
	/** Whether to escape HTML characters. The default value is false. */
	private boolean escapeHtml = false;

	/** Velocity template content */
	private String templateContent;

	/**
	 * Whether any velocity exception should be trapped and displayed on the
	 * panel (false) or thrown up to be handled by the exception mechanism of
	 * Wicket (true). The default is false, which traps and displays any
	 * exception without having consequences for the other components on the
	 * page.
	 * <p>
	 * Trapping these exceptions without disturbing the other components is
	 * especially usefull in CMS like applications, where 'normal' users are
	 * allowed to do basic scripting. On errors, you want them to be able to
	 * have them correct them while the rest of the application keeps on
	 * working.
	 */
	private boolean throwVelocityExceptions = false;

	/**
	 * Construct.
	 * 
	 * @param name
	 *            See Component
	 * @param templateResource
	 *            The velocity template as a string resource
	 * @param model
	 *            MapModel with variables that can be substituted by Velocity
	 */
	public VelocityPanel(final String name, final IStringResourceStream templateResource,
			final Model model)
	{
		super(name, model);
		if(templateResource != null){
			final String template = templateResource.asString();
			if (template != null){
				templateContent = template;
			}
		}
	}

	/**
	 * Gets whether to escape HTML characters.
	 * 
	 * @return whether to escape HTML characters
	 */
	public final boolean getEscapeHtml()
	{
		return escapeHtml;
	}

	/**
	 * Gets whether any velocity exception should be trapped and displayed on
	 * the panel (false) or thrown up to be handled by the exception mechanism
	 * of Wicket (true). The default is true, which traps and displays any
	 * exception without having consequences for the other components on the
	 * page.
	 * 
	 * @return Whether any velocity exceptions should be thrown or trapped.
	 */
	public final boolean getThrowVelocityExceptions()
	{
		return throwVelocityExceptions;
	}

	/**
	 * Sets whether to escape HTML characters. The default value is false.
	 * 
	 * @param escapeHtml
	 *            whether to escape HTML characters
	 * @return This
	 */
	public final VelocityPanel setEscapeHtml(boolean escapeHtml)
	{
		this.escapeHtml = escapeHtml;
		return this;
	}

	/**
	 * Gets whether any velocity exception should be trapped and displayed on
	 * the panel (true) or thrown up to be handled by the exception mechanism of
	 * Wicket (false).
	 * 
	 * @param throwVelocityExceptions
	 *            whether any exception should be trapped or rethrown
	 * @return This
	 */
	public final VelocityPanel setThrowVelocityExceptions(boolean throwVelocityExceptions)
	{
		this.throwVelocityExceptions = throwVelocityExceptions;
		return this;
	}

	/**
	 * @see wicket.Component#onComponentTagBody(wicket.markup.MarkupStream,
	 *      wicket.markup.ComponentTag)
	 */
	protected void onComponentTagBody(final MarkupStream markupStream,
			final ComponentTag openTag)
	{
		final Reader templateReader = getTemplateReader();
		if (templateReader != null)
		{
			// Get model as a map
			final Map map = (Map)getModelObject();

			// create a Velocity context object using the model if set
			final VelocityContext ctx = new VelocityContext(map);

			// create a writer for capturing the Velocity output
			StringWriter writer = new StringWriter();

			// string to be used as the template name for log messages in case
			// of error
			final String logTag = getId();
			try
			{
				Velocity.init();
				// execute the velocity script and capture the output in writer
				Velocity.evaluate(ctx, writer, logTag, templateReader);

				// replace the tag's body the Velocity output
				String result = writer.toString();

				if (escapeHtml)
				{
					// encode the result in order to get valid html output that
					// does not break the rest of the page
					result = Strings.escapeMarkup(result).toString();
				}

				// now replace the body of the tag with the velocity merge
				// result
				replaceComponentTagBody(markupStream, openTag, result);
			}
			catch (ParseErrorException e)
			{
				onException(e, markupStream, openTag);
			}
			catch (MethodInvocationException e)
			{
				onException(e, markupStream, openTag);
			}
			catch (ResourceNotFoundException e)
			{
				onException(e, markupStream, openTag);
			}
			catch (IOException e)
			{
				onException(e, markupStream, openTag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				onException(e, markupStream, openTag);
			}
		}
		else
		{
			replaceComponentTagBody(markupStream, openTag, ""); // just empty it
		}
	}

	/**
	 * Gets a reader for the velocity template.
	 * 
	 * @return reader for the velocity template
	 */
	private Reader getTemplateReader()
	{		
		return templateContent == null? null:new StringReader(templateContent);
	}

	/**
	 * Either print or rethrow the throwable.
	 * 
	 * @param exception
	 *            the cause
	 * @param markupStream
	 *            the markup stream
	 * @param openTag
	 *            the open tag
	 */
	private void onException(final Exception exception, final MarkupStream markupStream,
			final ComponentTag openTag)
	{
		if (!throwVelocityExceptions)
		{
			// print the exception on the panel
			String stackTraceAsString = Strings.toString(exception);
			replaceComponentTagBody(markupStream, openTag, stackTraceAsString);
		}
		else
		{
			// rethrow the exception
			throw new WicketRuntimeException(exception);
		}
	}
}