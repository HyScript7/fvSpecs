<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]


<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/HyScript7/fvSpecs">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">FvSpecs</h3>

  <p align="center">
    A plugin for Minecraft 1.20.X aiming to make pvp on survival servers more interesting & engaging!
    <br />
    <a href="https://github.com/HyScript7/fvSpecs"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://discord.me/scriptnetwork">Join Discord Server</a>
    ·
    <a href="https://github.com/HyScript7/fvSpecs/issues">Report Bug</a>
    ·
    <a href="https://github.com/HyScript7/fvSpecs/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Name Screen Shot][product-screenshot]](https://example.com)

FvSpecs is a minecraft plugin with the goal of making pvp on SMP servers more interesting by adding a deathban-like mechanic, magic and special abilities unique to each player. The inspiration comes from BlissSMP's Gems and some of Verdant Moon's mechanics. If you have any suggestions, you can fill out [this form](https://docs.google.com/forms/d/e/1FAIpQLSezhz1kPrp9ZNwt9fvidK_mE6ZbrBTApRHGJHi_aOeXu_xQbw/viewform?usp=sf_link) or make a fork and implement your ideas your self.

Here is an overview of some mechanics:

- Player Stats: Adds LV, Prestige and ExP stats which scale with kills in pvp (or less efficiently pve).
- Voided by Death: Every player has 10 or so lives. When they die to another player, they may lose a life (depending on the attacker's choice). Once a player reaches 0 lives, they are sent to the void dimension.
- Bounties: When killing a player, players gain bounty calculated from the level difference between attacker and victim.
- Void Realm: Players can find naturally generating wells that lead to the Void Dimension, a place void of life, designed to be a nuisance for living beings.
- Races: Players can spawn with either the default Human race, or a randomly chosen race, which slightly affects how they play minecraft.
- Specs: The main focus of the plugin - Unique abilities which are assigned to each player when they first join. A player's spec is supposed to be their ultimate weapon, making combat more dangerous when you don't know your opponents ability, and more importantly it's weakness.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

- [![Java][java-shield]][java-url]
- [![Gradle][gradle-shield]][gradle-url]
- [PaperMC](https://papermc.io/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

This section will walk you through using the plugin in development or production.

### Development Installation

For contributions, make sure you have gradle and Java 17 installed.

1. Clone the repository:
  ```
  git clone https://github.com/HyScript7/fvSpecs.git
  # or for ssh
  git clone git@github.com:HyScript7/fvSpecs.git
  ```
2. Open the project using your preferred IDE
3. (optional) Do a clean build using gradle:
  ```
  ./gradlew clean build
  ```

### Installation

1. Download the latest plugin jar and datapack zip from the [Releases page](https://github.com/HyScript7/fvSpecs/releases)
2. Move the jar into your server's plugins folder
3. Move the zip file into your server's world/datapacks folder
4. Restart your server (do not use /reload or plugman's load command, they break things!)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

TODO: This section will be finished once there is a working release.

_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [x] Void Realm
  - [ ] Castle Structure
  - [x] Bounty Board Structure
- [x] Player Statistics
  - [x] Prestige
- [x] Lives System
  - [x] Reviving voided players
  - [ ] Live reobtainement ritual (Quest)
- [x] Bounties
  - [ ] Bounty hunting (Quest)
  - [ ] Turn-in system
- [ ] Innate Names
- [ ] Specs
  - [ ] Spec API & Addon Support
- [ ] Races
  - [ ] Race API & Addon Support
- [ ] Magic & Spells
  - [ ] Spell API & Addon Support

See the [open issues](https://github.com/HyScript7/fvSpecs/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.md` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

HyScript7 - [@hyscript7](https://twitter.com/hyscript7) - hyscript7@gmail.com

Project Link: [https://github.com/HyScript7/fvSpecs](https://github.com/HyScript7/fvSpecs)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [README Template](https://github.com/othneildrew/Best-README-Template)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/HyScript7/fvSpecs.svg?style=for-the-badge
[contributors-url]: https://github.com/HyScript7/fvSpecs/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/HyScript7/fvSpecs.svg?style=for-the-badge
[forks-url]: https://github.com/HyScript7/fvSpecs/network/members
[stars-shield]: https://img.shields.io/github/stars/HyScript7/fvSpecs.svg?style=for-the-badge
[stars-url]: https://github.com/HyScript7/fvSpecs/stargazers
[issues-shield]: https://img.shields.io/github/issues/HyScript7/fvSpecs.svg?style=for-the-badge
[issues-url]: https://github.com/HyScript7/fvSpecs/issues
[license-shield]: https://img.shields.io/github/license/HyScript7/fvSpecs.svg?style=for-the-badge
[license-url]: https://github.com/HyScript7/fvSpecs/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/linkedin_username
[product-screenshot]: images/screenshot.png
[java-shield]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[gradle-shield]: https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white
[java-url]: https://www.java.com/en/
[gradle-url]: https://gradle.org/
