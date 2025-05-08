package com.shashank.expense.tracker.core.common

object Constants {
    const val DATABASE_NAME = "expense_tracker.db"
    const val PREFERENCES_NAME = "expense_tracker_prefs"
    
    object Routes {
        const val SPLASH = "splash"
        const val ONBOARDING = "onboarding"
        const val LOGIN = "login"
        const val REGISTER = "register"
        const val DASHBOARD = "dashboard"
        const val TRANSACTIONS = "transactions"
        const val BUDGETS = "budgets"
        const val REPORTS = "reports"
        const val CATEGORIES = "categories"
        const val ACCOUNTS = "accounts"
        const val GOALS = "goals"
        const val SETTINGS = "settings"
    }
    
    object Preferences {
        const val IS_FIRST_RUN = "is_first_run"
        const val AUTH_TOKEN = "auth_token"
        const val USER_ID = "user_id"
        const val THEME_MODE = "theme_mode"
        const val CURRENCY = "currency"
    }
} 