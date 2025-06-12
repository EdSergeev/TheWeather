#!/bin/bash

# OpenWeatherMap Icon Downloader
# Downloads all weather icons in @2x resolution and renames them

echo "Starting OpenWeatherMap icon download..."

# Create icons directory if it doesn't exist
mkdir -p icons
cd icons

# Array of all icon codes
icons=(
    "01d" "01n"  # clear sky
    "02d" "02n"  # few clouds
    "03d" "03n"  # scattered clouds
    "04d" "04n"  # broken clouds
    "09d" "09n"  # shower rain
    "10d" "10n"  # rain
    "11d" "11n"  # thunderstorm
    "13d" "13n"  # snow
    "50d" "50n"  # mist/fog/atmosphere
)

# Base URL for OpenWeatherMap icons
base_url="https://openweathermap.org/img/wn"

# Download each icon
for icon in "${icons[@]}"; do
    echo "Downloading ${icon}..."

    # Download with @2x resolution and save with 'ic_' prefix
    curl -s -o "ic_${icon}.png" "${base_url}/${icon}@2x.png"

    # Check if download was successful
    if [ $? -eq 0 ]; then
        echo "✓ Successfully downloaded ic_${icon}.png"
    else
        echo "✗ Failed to download ic_${icon}.png"
    fi
done

echo ""
echo "Download complete! All icons saved in the 'icons' directory."
echo "Total icons downloaded: ${#icons[@]}"