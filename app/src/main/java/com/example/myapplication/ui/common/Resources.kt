package com.example.myapplication.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.myapplication.R


@Composable
fun rememberMainScreenResources(): MainScreenResources {
    return MainScreenResources(
        appName = stringResource(R.string.app_name),
        searchString = stringResource(R.string.search),
        playlistsString = stringResource(R.string.playlists),
        favoritesString = stringResource(R.string.favorites),
        settingsString = stringResource(R.string.settings),
        playlistsToast = stringResource(R.string.playlists_toast),
        favoritesToast = stringResource(R.string.favorites_toast),

        primaryBlue = colorResource(R.color.primary_blue),
        white = colorResource(R.color.white),
        black = colorResource(R.color.black),
        textPrimary = colorResource(R.color.text_primary),
        gray = colorResource(R.color.gray),

        titleFontSize = dimensionResource(R.dimen.main_title_font_size).value.sp,
        titlePaddingTop = dimensionResource(R.dimen.main_title_padding_top),
        titlePaddingStart = dimensionResource(R.dimen.main_title_padding_start),
        bottomSheetCornerRadius = dimensionResource(R.dimen.bottom_sheet_corner_radius),
        bottomSheetPaddingTop = dimensionResource(R.dimen.bottom_sheet_padding_top),
        bottomSheetPaddingHorizontal = dimensionResource(R.dimen.bottom_sheet_padding_horizontal),
        menuItemHeight = dimensionResource(R.dimen.menu_item_height),
        menuItemPaddingVertical = dimensionResource(R.dimen.menu_item_padding_vertical),
        menuItemPaddingHorizontal = dimensionResource(R.dimen.menu_item_padding_horizontal),
        menuItemIconSize = dimensionResource(R.dimen.menu_item_icon_size),
        menuItemTextFontSize = dimensionResource(R.dimen.menu_item_text_font_size).value.sp,
        menuItemTextLineHeight = dimensionResource(R.dimen.menu_item_text_line_height).value.sp
    )
}

@Composable
fun rememberSearchScreenResources(): SearchScreenResources {
    return SearchScreenResources(
        searchString = stringResource(R.string.search),
        searchHint = stringResource(R.string.search_hint),
        backString = stringResource(R.string.back),
        clearString = stringResource(R.string.clear),
        searchInitialMessage = stringResource(R.string.search_initial_message),
        searchingString = stringResource(R.string.searching),
        noResultsString = stringResource(R.string.no_results),
        searchErrorString = stringResource(R.string.search_error),
        retryString = stringResource(R.string.retry),

        white = colorResource(R.color.white),
        black = colorResource(R.color.black),
        gray = colorResource(R.color.gray),
        searchBackground = colorResource(R.color.search_background),
        divider = colorResource(R.color.divider),

        bottomSheetPaddingHorizontal = dimensionResource(R.dimen.bottom_sheet_padding_horizontal),
        bottomSheetPaddingTop = dimensionResource(R.dimen.bottom_sheet_padding_top),
        searchTitleFontSize = dimensionResource(R.dimen.search_title_font_size).value.sp,
        searchBarHeight = dimensionResource(R.dimen.search_bar_height),
        searchBarCornerRadius = dimensionResource(R.dimen.search_bar_corner_radius),
        searchBarPaddingHorizontal = dimensionResource(R.dimen.search_bar_padding_horizontal),
        iconSizeSmall = dimensionResource(R.dimen.icon_size_small),
        iconSizeMedium = dimensionResource(R.dimen.icon_size_medium),
        searchHintFontSize = dimensionResource(R.dimen.search_hint_font_size).value.sp,
        searchTextFontSize = dimensionResource(R.dimen.search_text_font_size).value.sp
    )
}

@Composable
fun rememberSettingsScreenResources(): SettingsScreenResources {
    return SettingsScreenResources(
        settingsTitle = stringResource(R.string.settings),
        darkThemeString = stringResource(R.string.dark_theme),
        shareAppString = stringResource(R.string.share_app),
        supportString = stringResource(R.string.support),
        termsString = stringResource(R.string.terms_of_use),
        backString = stringResource(R.string.back),
        appName = stringResource(R.string.app_name),
        shareText = stringResource(R.string.share_text),
        supportEmail = stringResource(R.string.support_email),
        supportSubject = stringResource(R.string.support_subject),
        supportBody = stringResource(R.string.support_body),
        termsUrl = stringResource(R.string.terms_url),
        contactSupport = stringResource(R.string.contact_support),

        white = colorResource(R.color.white),
        black = colorResource(R.color.black),
        gray = colorResource(R.color.gray),

        bottomSheetPaddingTop = dimensionResource(R.dimen.bottom_sheet_padding_top),
        bottomSheetPaddingHorizontal = dimensionResource(R.dimen.bottom_sheet_padding_horizontal),
        iconSizeMedium = dimensionResource(R.dimen.icon_size_medium),
        searchTitleFontSize = dimensionResource(R.dimen.search_title_font_size).value.sp,
        settingsItemHeight = dimensionResource(R.dimen.settings_item_height),
        menuItemPaddingVertical = dimensionResource(R.dimen.menu_item_padding_vertical),
        menuItemPaddingHorizontal = dimensionResource(R.dimen.menu_item_padding_horizontal),
        settingsTextFontSize = dimensionResource(R.dimen.settings_text_font_size).value.sp,
        settingsTextLineHeight = dimensionResource(R.dimen.settings_text_line_height).value.sp
    )
}

data class MainScreenResources(
    val appName: String,
    val searchString: String,
    val playlistsString: String,
    val favoritesString: String,
    val settingsString: String,
    val playlistsToast: String,
    val favoritesToast: String,

    val primaryBlue: Color,
    val white: Color,
    val black: Color,
    val textPrimary: Color,
    val gray: Color,

    val titleFontSize: TextUnit,
    val titlePaddingTop: Dp,
    val titlePaddingStart: Dp,
    val bottomSheetCornerRadius: Dp,
    val bottomSheetPaddingTop: Dp,
    val bottomSheetPaddingHorizontal: Dp,
    val menuItemHeight: Dp,
    val menuItemPaddingVertical: Dp,
    val menuItemPaddingHorizontal: Dp,
    val menuItemIconSize: Dp,
    val menuItemTextFontSize: TextUnit,
    val menuItemTextLineHeight: TextUnit
)

data class SearchScreenResources(
    val searchString: String,
    val searchHint: String,
    val backString: String,
    val clearString: String,
    val searchInitialMessage: String,
    val searchingString: String,
    val noResultsString: String,
    val searchErrorString: String,
    val retryString: String,

    val white: Color,
    val black: Color,
    val gray: Color,
    val searchBackground: Color,
    val divider: Color,

    val bottomSheetPaddingHorizontal: Dp,
    val bottomSheetPaddingTop: Dp,
    val searchTitleFontSize: TextUnit,
    val searchBarHeight: Dp,
    val searchBarCornerRadius: Dp,
    val searchBarPaddingHorizontal: Dp,
    val iconSizeSmall: Dp,
    val iconSizeMedium: Dp,
    val searchHintFontSize: TextUnit,
    val searchTextFontSize: TextUnit
)

data class SettingsScreenResources(
    val settingsTitle: String,
    val darkThemeString: String,
    val shareAppString: String,
    val supportString: String,
    val termsString: String,
    val backString: String,
    val appName: String,
    val shareText: String,
    val supportEmail: String,
    val supportSubject: String,
    val supportBody: String,
    val termsUrl: String,
    val contactSupport: String,

    val white: Color,
    val black: Color,
    val gray: Color,

    val bottomSheetPaddingTop: Dp,
    val bottomSheetPaddingHorizontal: Dp,
    val iconSizeMedium: Dp,
    val searchTitleFontSize: TextUnit,
    val settingsItemHeight: Dp,
    val menuItemPaddingVertical: Dp,
    val menuItemPaddingHorizontal: Dp,
    val settingsTextFontSize: TextUnit,
    val settingsTextLineHeight: TextUnit
)