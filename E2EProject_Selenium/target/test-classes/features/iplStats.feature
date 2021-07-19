Feature: IPL Stats

Scenario Outline: Get player with highest runs
Given Initialize browser with chrome
And navigate to "https://www.iplt20.com/matches/schedule/men" site
When User navigates to <menu> and select <option>
Then Verify player with highest runs

Examples:
|menu                |option       |
|STATS               |By Season    |
