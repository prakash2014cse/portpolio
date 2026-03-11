# manpowersupply

A mobile-responsive hybrid web app demo for managing:
- Manpower supply
- Industrial equipment inventory
- Project allocations

## Demo features
- Dashboard cards with live operational counts
- Worker roster with skill tag filters and search
- Equipment status tracking (available / in-use / maintenance)
- Project allocation board with one-click demo request creation
- Mock JSON data source (`data/mock-data.json`)

## Run locally
Because the app loads JSON with `fetch`, run it using a local server:

```bash
python3 -m http.server 4173
```

Then open:

```text
http://localhost:4173
```
