package com.muhammad.presentation.productScreen

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.core.network.model.Product
import com.muhammad.presentation.main.MainViewModel

@Composable
fun BottomBar(
  viewModel: MainViewModel,
  selectedProduct: (Product) -> Unit  // state
) {
  val isLoading: Boolean by viewModel.isLoading
  val selectedTab = NanoHomeTab.getTabFromResource(viewModel.selectedTab.value)
  val tabs = NanoHomeTab.values()

  ConstraintLayout {
    val (body, progress) = createRefs()
    Scaffold(
      backgroundColor = colorResource(id = com.muhammad.core_ui.R.color.light_white),
      topBar = { PosterAppBar() },
      modifier = Modifier.constrainAs(body) {
        top.linkTo(parent.top)
      },
      bottomBar = {
        BottomNavigation(
          backgroundColor = Color.White,
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp)),
        ) {
          tabs.forEach { tab ->
            BottomNavigationItem(
              icon = {
                Icon(
                  painter = painterResource(id =tab.icon ), contentDescription = null
                )
              },
//              label = { Text(text = stringResource(tab.title), color = Color.White) },
              selected = tab == selectedTab,
              onClick = { viewModel.selectTab(tab.title) },
              selectedContentColor = LocalContentColor.current,
              unselectedContentColor = LocalContentColor.current,
            )
          }
        }
      }
    ) { innerPadding ->
      val modifier = Modifier.padding(innerPadding)
      Crossfade(selectedTab) { destination ->
        when (destination) {
          NanoHomeTab.HOME -> HomeProductScreen(modifier, selectedProduct)
          NanoHomeTab.CART -> CartScreen(modifier)
          NanoHomeTab.FAVOURITE -> FavouriteScreen(modifier)
          NanoHomeTab.PROFILE -> ProfileScreen(modifier)

        }
      }
    }
    if (isLoading) {
      CircularProgressIndicator(
        modifier = Modifier
          .constrainAs(progress) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
          }
      )
    }
  }

}

@Preview
@Composable
private fun PosterAppBar() {
  TopAppBar(
    elevation = 8.dp,
    backgroundColor = Color.White,
    modifier = Modifier
      .statusBarsPadding()
      .height(58.dp)
      .fillMaxWidth()
      .clip(RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp))
  ) {
    Text(
      modifier = Modifier
        .padding(8.dp)
        .fillMaxSize()
        .align(Alignment.CenterVertically),
      textAlign = TextAlign.Center,
      text = stringResource(com.muhammad.core_ui.R.string.title),
      color = Color.Black,
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold
    )
  }
}


enum class NanoHomeTab(
  @StringRes val title: Int,
  val icon: Int
) {
  HOME(com.muhammad.core_ui.R.string.menu_home, com.muhammad.core_ui.R.drawable.home_),
  CART(com.muhammad.core_ui.R.string.menu_cart, com.muhammad.core_ui.R.drawable.cart_),
  FAVOURITE(com.muhammad.core_ui.R.string.menu_favourite, com.muhammad.core_ui.R.drawable.like_),
  PROFILE(com.muhammad.core_ui.R.string.menu_profile, com.muhammad.core_ui.R.drawable.user_);


  companion object {
    fun getTabFromResource(@StringRes resource: Int): NanoHomeTab {
      return when (resource) {
        com.muhammad.core_ui.R.string.menu_cart -> CART
        com.muhammad.core_ui.R.string.menu_favourite -> FAVOURITE
        com.muhammad.core_ui.R.string.menu_profile -> PROFILE
        else -> HOME
      }
    }
  }
}


