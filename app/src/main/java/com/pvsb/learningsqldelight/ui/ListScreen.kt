package com.pvsb.learningsqldelight.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import learning.persondb.PersonEntity

@Composable
fun ListScreen(
    viewModel: PersonsListViewModel = hiltViewModel()
) {

    val persons = viewModel.personsState.collectAsState(initial = emptyList()).value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(persons) {
                    PersonCell(person = it, onItemCLick = {
                        viewModel.getPersonById(it.id)
                    }, onDeleteClick = {
                        viewModel.onDeleteIconClick(it.id)
                    }, modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(value = viewModel.firstName, onValueChange = {
                    viewModel.onFirstNameChanged(it)
                }, placeholder = {
                    Text(text = "first name")
                }, modifier = Modifier.weight(1f))

                Spacer(modifier = Modifier.width(20.dp))

                TextField(value = viewModel.lastName, onValueChange = {
                    viewModel.onLastNameChanged(it)
                }, placeholder = {
                    Text(text = "last name")
                }, modifier = Modifier.weight(1f))

                Button(onClick = { viewModel.onInsertPersonClick() }) {
                    Text(text = "Insert")
                }
            }
        }

        viewModel.personDetail?.let {
            Dialog(onDismissRequest = { viewModel.onPersonDetailsDismiss() }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "${it.firstName} ${it.lastName}")
                }
            }
        }
    }
}

@Composable
fun PersonCell(
    modifier: Modifier = Modifier,
    person: PersonEntity,
    onItemCLick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {

    Row(
        modifier = modifier.clickable { onItemCLick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = person.firstName, fontSize = 22.sp, fontWeight = FontWeight.SemiBold
        )

        IconButton(onClick = { onDeleteClick() }) {
            Icon(imageVector = Icons.Rounded.Delete, contentDescription = "", tint = Color.Black)
        }
    }
}