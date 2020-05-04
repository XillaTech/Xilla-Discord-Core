# Xilla Discord Core<br>
Managed and Developed by Tobias Steely @ https://xilla.net<br>
I offer official hosting starting @ $1.50/Month - https://xilla.net/cart.php?gid=6<br><br>

# Version / Download<br>
Latest Stable Version : 1.0.8 - <a href="http://api.xilla.net/download/xilla-discord-core.jar">Download here</a><br>
Current Development Version : 1.0.9 - https://github.com/Alontrle/Xilla-Discord-Core/tree/master/build/libs<br><br>

# Installing<br>
Easy Install Script for Linux - "bash <(curl -Ss https://api.xilla.net/download/install.sh)"<br><br>

<a href="https://pterodactyl.io/">Pterodactyl Panel</a> Egg - https://api.xilla.net/download/egg-xilla-discord-core.json<br><br>

Manual Installation : <br>
 1 - Install Java 14<br>
 --- Ubuntu 14.04 - <a href="https://www.atlantic.net/hipaa-compliant-cloud-storage/how-to-install-java-ubuntu-14-04/">Click Here</a><br>
 --- Ubuntu 18.04 - <a href="https://computingforgeeks.com/install-oracle-java-openjdk-14-on-ubuntu-debian-linux/">Click Here</a><br>
 --- CentOS 7 / 8 or Fedora - <a href="https://computingforgeeks.com/install-oracle-java-openjdk-14-on-centosfedora-linux/">Click Here</a><br>
 2 - Download the bot from <a href="http://api.xilla.net/download/xilla-discord-core.jar">here</a><br>
 3 - Run the bot, and input your settings. (java -jar xilla-discord-core.jar)<br>
 4 - Download the modules you want, and put them in the /modules/ folder.<br>
 5 - Restart your bot and input any required settings.<br>
 
# Updating<br>
The Core<br>
 1 - Navigate to your installation (Easy installer's location is /srv/xilla-discord-core/)<br>
 2 - Delete the bot.jar<br>
 3 - Upload the newer updated bot.jar from <a href="http://api.xilla.net/download/xilla-discord-core.jar">here</a><br>
 4 - Start the bot and fill in any missing settings.<br><br>

A Module<br>
 1 - Navigate to your installation (Easy installer's location is /srv/xilla-discord-core/)<br>
 2 - Open the /modules/ folder.<br>
 3 - Delete the old module<br>
 4 - Upload the newer updated module<br>
 5 - Start the bot and fill in any missing settings.<br><br>

# Available Modules<br> 
All modules are available for free with branding in the footer, you can purchase a license if you'd like to remove this branding.<br>
Tickets - <a href="http://api.xilla.net/download/xilla-ticket-bot.jar">Download here</a><br>
Music - <a href="http://api.xilla.net/download/xilla-music-bot.jar">Download here</a><br>
Moderation - Coming Soon<br>
<a href="https://xilla.net/cart.php?gid=5">Remove Branding Here</a><br><br>

# Pterodactyl Panel Supported<br>
Setup our Pterodactyl Panel egg for easy bot hosting. Checkout Pterodactyl <a href="https://pterodactyl.io/">here</a>. Download the bot egg <a href="https://api.xilla.net/download/egg-xilla-discord-core.json">here</a><br><br>

# Creating a module<br>
We will add an actual wiki guide soon, but for now here is a sample module. https://github.com/Alontrle/Xilla-Discord-Core/tree/master/Modules/ExampleModule<br><br>

# Documentation<br>
Java Docs - https://alontrle.github.io/Xilla-Discord-Core<br><br>

# Compiling / Editing<br>
We use gradle, so make sure to use gradle and use our build file. I reccomend using ShadowJar to create an UBER Jar and use that as your library. That is how I compile the public releases.
