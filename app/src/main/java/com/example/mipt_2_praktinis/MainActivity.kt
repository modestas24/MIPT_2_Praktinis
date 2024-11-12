package com.example.mipt_2_praktinis

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.mipt_2_praktinis.ui.theme.CustomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomTheme {
                val options = listOf("words", "letters")
                val current = remember { mutableStateOf(options[0]) }
                val count = remember { mutableIntStateOf(0) }
                var text by remember { mutableStateOf("") }

                Scaffold(
                    floatingActionButton = {
                        CustomFloatingActionButton(
                            iconVector = Icons.Rounded.CheckCircle,
                            text = stringResource(R.string.count),
                            onClick = {
                                countText(text, count, current.value)

                                if (count.intValue <= 0) {
                                    val toast = Toast.makeText(
                                        this,
                                        getString(R.string.empty_field),
                                        Toast.LENGTH_SHORT
                                    )
                                    toast.show()
                                }
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        ) {
                            CustomText(
                                if (count.intValue <= 0) stringResource(R.string.empty_count) else count.intValue.toString(),
                                modifier = Modifier
                                    .height(58.dp)
                                    .testTag("CountOutput")
                            )
                            Spacer(Modifier.width(32.dp))
                            CustomSpinner(options, current)
                        }
                        Spacer(Modifier.height(16.dp))
                        OutlinedTextField(
                            text,
                            shape = RoundedCornerShape(8.dp),
                            textStyle = TextStyle(
                                fontSize = TextUnit.Unspecified,
                            ),
                            onValueChange = { text = it },
                            label = {
                                Text(stringResource(R.string.enter_text))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.Create,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp)
                                .wrapContentHeight(Alignment.CenterVertically)
                                .defaultMinSize(minHeight = 58.dp)
                                .testTag("CountInput")
                        )
                    }
                }
            }
        }
    }
}

fun countText(text: String, state: MutableState<Int>, option: String = "letters") {
    var value by state
    value = if (text.isEmpty()) 0
    else if (option == "letters") text.length
    else if (option == "words") text.trim().replace("\\s+".toRegex(), " ").split(" ").count()
    else 0
}

@Composable
fun CustomText(text: String, modifier: Modifier) {
    Text(
        text,
        softWrap = false,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = TextUnit.Unspecified,
            fontWeight = FontWeight.Bold,
        ),
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .wrapContentHeight(Alignment.CenterVertically)
    )
}

@Composable
fun CustomFloatingActionButton(
    text: String,
    onClick: () -> Unit,
    iconVector: ImageVector?
) {
    FloatingActionButton(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        content = {
            Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                if (iconVector != null) {
                    Icon(imageVector = iconVector, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(text)
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSpinner(options: List<String>, state: MutableState<String>) {
    var current by state
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(8.dp),
            value = current,
            onValueChange = { current = it },
            singleLine = true,
            readOnly = true,
            textStyle = TextStyle(
                fontSize = TextUnit.Unspecified,
            ),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded,
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .padding(0.dp)
                .menuAnchor()
                .wrapContentHeight(Alignment.CenterVertically)
                .height(58.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = { current = it }
                )
            }
        }
    }
}
