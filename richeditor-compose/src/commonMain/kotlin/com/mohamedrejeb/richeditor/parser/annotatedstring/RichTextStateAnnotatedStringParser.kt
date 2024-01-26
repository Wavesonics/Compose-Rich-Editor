package com.mohamedrejeb.richeditor.parser.annotatedstring

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import com.mohamedrejeb.richeditor.model.RichSpan
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.parser.RichTextStateParser
import com.mohamedrejeb.richeditor.parser.markdown.RichTextStateMarkdownParser
import com.mohamedrejeb.richeditor.utils.fastForEach
import com.mohamedrejeb.richeditor.utils.fastForEachIndexed

internal object RichTextStateAnnotatedStringParser : RichTextStateParser<AnnotatedString> {

	override fun encode(input: AnnotatedString): RichTextState {
		TODO("Not yet implemented")
	}

	override fun decode(richTextState: RichTextState): AnnotatedString {
		val builder = AnnotatedString.Builder()
		richTextState.richParagraphList.fastForEachIndexed { index, richParagraph ->
			richParagraph.getFirstNonEmptyChild()?.let { firstNonEmptyChild ->
				if (firstNonEmptyChild.text.isNotEmpty()) {
					builder.append(
						firstNonEmptyChild.span
					)
				}
			}

			// Append paragraph children
			richParagraph.children.fastForEach { richSpan ->
				builder.append(RichTextStateMarkdownParser.decodeRichSpanToMarkdown(richSpan))
			}

			if (index < richTextState.richParagraphList.lastIndex) {
				// Append new line
				builder.append("\n")
			}
		}

		return builder.toAnnotatedString()
	}

	private fun decodeRichSpanToMarkdown(richSpan: RichSpan): String {
		val stringBuilder = StringBuilder()

		// Check if span is empty
		if (richSpan.isEmpty()) return ""

		// Convert span style to CSS string
		var markdownOpen = ""
		if ((richSpan.spanStyle.fontWeight?.weight ?: 400) > 400) markdownOpen += "**"
		if (richSpan.spanStyle.fontStyle == FontStyle.Italic) markdownOpen += "*"
		if (richSpan.spanStyle.textDecoration == TextDecoration.LineThrough) markdownOpen += "~~"

		// Append markdown open
		stringBuilder.append(markdownOpen)

		// Apply rich span style to markdown
		val spanMarkdown = RichTextStateMarkdownParser.decodeMarkdownElementFromRichSpan(richSpan.text, richSpan.style)

		// Append text
		stringBuilder.append(spanMarkdown)

		// Append children
		richSpan.children.fastForEach { child ->
			stringBuilder.append(decodeRichSpanToMarkdown(child))
		}

		// Append markdown close
		stringBuilder.append(markdownOpen.reversed())

		return stringBuilder.toString()
	}
}