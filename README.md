# Remote Compose Android Player

The Android client for the [Remote Compose Demo](https://github.com/armcha/remotecompose) — a pure player app that downloads and renders server-driven UI using [AndroidX Remote Compose](https://developer.android.com/jetpack/androidx/releases/compose-remote).

<img src="app-screenshot.png" width="300" alt="App Screenshot">

## How It Works

The app downloads pre-built binary Remote Compose documents (`.rc` files) from GitHub and renders them natively using `RemoteComposePlayer`. No JSON parsing, no UI creation code — just binary bytes in, native UI out.

```
GitHub (remotecompose repo)              Android App
┌────────────────────────┐              ┌────────────────────────┐
│  config.rc             │              │                        │
│  config_detail.rc      │  OkHttp GET  │  RemoteComposePlayer   │
│  config_estimates.rc   │ ───────────> │  renders binary docs   │
│  config_estimate_...rc │   ByteArray  │                        │
└────────────────────────┘              └────────────────────────┘
```

## Setup

1. Clone this repository
2. Add your GitHub token to `local.properties`:
   ```
   GITHUB_TOKEN=your_github_token_here
   ```
3. Build and run in Android Studio

The token is needed to fetch `.rc` files from the GitHub API with higher rate limits. It is read via `BuildConfig` and never committed to source control.

## Architecture

```
app/src/main/java/com/example/remotecompose/
├── MainActivity.kt              Entry point (edge-to-edge + Compose)
├── MainViewModel.kt             Fetches .rc documents, manages UI state
├── data/remote/
│   └── RemoteConfigFetcher.kt   OkHttp client for downloading binary docs
├── navigation/
│   └── AppNavigation.kt         Compose Navigation with 4 screens
├── ui/components/
│   ├── RemoteDocumentView.kt    AndroidView wrapper for RemoteComposePlayer
│   ├── RemoteScreenTopBar.kt    Top bar with refresh + last updated time
│   └── ErrorContent.kt          Error state with retry
├── ui/screen/
│   └── RemoteScreen.kt          Screen composable (player + toolbar)
└── ui/theme/
    ├── Color.kt                 Material 3 color palette
    ├── Theme.kt                 Light/dark theme with dynamic colors
    └── Type.kt                  Typography
```

## Screens

| Screen | Config | Description |
|---|---|---|
| Home | `config.rc` | Welcome screen with feature cards |
| Detail | `config_detail.rc` | Feature showcase with click actions |
| Estimates | `config_estimates.rc` | Estimate card with nested layout |
| Estimate Detail | `config_estimate_detail.rc` | Detailed estimate breakdown |

Navigation between screens is driven by click actions in the Remote Compose documents (e.g., `navigate:detail`, `navigate:estimates`).

## Dependencies

| Library | Purpose |
|---|---|
| `remote-core` | Remote Compose core types |
| `remote-player-core` | Document parsing |
| `remote-player-view` | `RemoteComposePlayer` (View-based renderer) |
| `okhttp` | HTTP client for fetching binary documents |
| `navigation-compose` | Compose Navigation |
| `lifecycle-viewmodel-compose` | ViewModel integration |

## Related

- [remotecompose](https://github.com/armcha/remotecompose) — Web editor, JVM converter, JSON configs, and generated binary documents
- [Live Editor](https://armcha.github.io/remotecompose/) — Drag-and-drop UI builder with live Compose/Wasm preview
- [Remote Compose documentation](https://github.com/androidx/androidx/tree/androidx-main/compose/remote/Documentation)
