package com.apress.wicketbook.components;

import wicket.extensions.markup.html.repeater.data.DataView;
import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.WebComponent;
import wicket.markup.html.navigation.paging.PagingNavigator;

public class CustomPagingNavigator extends PagingNavigator {
	public CustomPagingNavigator(String id, DataView dataView) {
		super(id, dataView);
		add(new HeadLine("headline", dataView));
	}

	class HeadLine extends WebComponent {
		private DataView dataView;

		public HeadLine(String id, DataView dataView) {
			super(id);
			this.dataView = dataView;
		}

		// Wicket callback method - explained after the code snippet.
		protected void onComponentTagBody(final MarkupStream markupStream,
				final ComponentTag openTag) {
			String text = getHeadlineText();
			replaceComponentTagBody(markupStream, openTag, text);
		}

		// Custom text providing more information about the items being
		// displayed,
		// etc.
		public String getHeadlineText() {
			int firstListItem = dataView.getCurrentPage()
					* dataView.getItemsPerPage();
			StringBuffer buf = new StringBuffer();
			// Construct the display string.
			buf.append(String.valueOf(dataView.getRowCount())).append(
					" items found, displaying ").append(
					String.valueOf(firstListItem + 1)).append(" to ").append(
					String.valueOf(firstListItem
							+ Math.min(dataView.getItemsPerPage(), dataView
									.getRowCount()))).append(".");
			return buf.toString();
		}
	}
}
