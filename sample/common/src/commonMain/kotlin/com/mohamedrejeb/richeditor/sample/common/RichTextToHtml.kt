package com.mohamedrejeb.richeditor.sample.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.RichTextValue
import com.mohamedrejeb.richeditor.parser.html.RichTextHtmlParser
import com.mohamedrejeb.richeditor.sample.common.ui.theme.ComposeRichEditorTheme
import com.mohamedrejeb.richeditor.ui.material3.OutlinedRichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichTextToHtml(
    modifier: Modifier = Modifier,
) {
    var richTextValue by remember { mutableStateOf(RichTextValue()) }
    val html = RichTextHtmlParser.decode(richTextValue)

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Text(
                text = "Rich Text Editor:",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

            RichTextStyleRow(
                value = richTextValue,
                onValueChanged = {
                    richTextValue = it
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedRichTextEditor(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = richTextValue,
                onValueChange = {
                    richTextValue = it
                },
            )
        }

        Spacer(Modifier.width(8.dp))

        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
        )

        Spacer(Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Text(
                text = "HTML code:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
            )

            Spacer(Modifier.height(8.dp))

            SelectionContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.extraSmall)
                    .padding(vertical = 12.dp, horizontal = 12.dp)
            ) {
                Text(
                    text = html,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}