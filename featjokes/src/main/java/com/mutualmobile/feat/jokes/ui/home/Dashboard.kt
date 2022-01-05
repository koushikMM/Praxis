package com.mutualmobile.feat.jokes.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mutualmobile.praxis.commonui.material.CommonTopAppBar
import com.mutualmobile.praxis.commonui.theme.PraxisSurface
import com.mutualmobile.praxis.commonui.theme.PraxisTheme
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.mutualmobile.praxis.commonui.theme.Typography

@Composable
fun Dashboard(homeVM: HomeVM = hiltViewModel()) {
  Scaffold(
    backgroundColor = PraxisTheme.colors.uiBackground,
    contentColor = PraxisTheme.colors.textSecondary,
    modifier = Modifier
      .statusBarsPadding()
      .navigationBarsPadding(),
    topBar = {
      CommonTopAppBar("Chuck Norris Random Joke Generator")
    }) {
    PraxisSurface(
      color = PraxisTheme.colors.uiBackground,
      modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
    ) {
      Column(
        modifier = Modifier
          .fillMaxHeight()
          .fillMaxWidth()
      ) {
        when (homeVM.viewState.value) {
          is HomeViewState.Loading -> {
            LinearProgressIndicator(
              color = PraxisTheme.colors.accent, modifier = Modifier
                .fillMaxWidth()
            )
          }
          is HomeViewState.ShowJokes -> {
            val jokes = (homeVM.viewState.value as HomeViewState.ShowJokes).jokes
            LazyColumn {
              items(jokes.size) { item ->
                ClickableText(text = buildAnnotatedString {
                  withStyle(
                    style = SpanStyle(
                      color = PraxisTheme.colors.textPrimary,
                      fontSize = Typography.body1.fontSize
                    )
                  ) {
                    append(jokes[item].joke)
                  }

                }, onClick = {
                  homeVM.showJoke(jokes[item].jokeId)
                }, modifier = Modifier.padding(16.dp))
              }
            }

          }
          is HomeViewState.Error -> {
            Text(text = "Error")
          }
        }
      }
    }
  }


}