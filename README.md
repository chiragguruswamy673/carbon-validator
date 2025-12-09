Project Overview
The Digital Carbon Footprint Validator is a unique QA automation project that goes beyond functional testing. It evaluates websites for:
- Performance metrics: page size (KB), load time (ms).
- Environmental impact: estimated grams of CO₂ emitted per visit.
- Accessibility & sustainability awareness: configurable thresholds to flag heavy or inefficient sites.
This project demonstrates creativity, technical depth, and social responsibility — making it recruiter‑friendly and portfolio‑worthy.

Features
- Multi‑site testing: Runs against multiple domains (e.g., Demoblaze, BBC, NDTV) in one suite.
- Custom JSON/HTML reports: Generates polished artifacts per site with metrics and pass/fail status.
- Configurable thresholds: Uses config.json to set default and domain‑specific CO₂ limits.
- CI/CD integration: Automated daily runs via GitHub Actions, with artifacts uploaded for download.
- Recruiter appeal: README badge shows live CI status, proving automation maturity.

Tech Stack
- Java + Selenium + TestNG → automation and test orchestration.
- WebDriverManager → driver setup.
- Custom utilities → performance metrics and CO₂ calculations.
- GitHub Actions → CI/CD pipeline with artifact uploads.

Example Output
Site=Demoblaze Host=demoblaze.com Size=680.63KB Load=12ms CO2=0.1361g Threshold=2.00g
Site=BBC Host=bbc.com Size=1717.84KB Load=26ms CO2=0.3436g Threshold=3.00g
Site=NDTV Host=ndtv.com Size=0.67KB Load=31ms CO2=0.0001g Threshold=2.50g


All sites passed their thresholds in this run.

Sustainability Context
- Demoblaze → A demo e‑commerce site, mainly used for performance and QA practice.
- BBC → Strong sustainability commitments, aiming for net zero by 2030.
- NDTV → Covers sustainability topics and reports in its corporate responsibility documents.
Verdict: BBC is the most sustainability‑aligned among the three, making it the best candidate to showcase in your reports.

Author:
Chirag Guruswamy
