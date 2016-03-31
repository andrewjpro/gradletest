package io.github.andrewjpro.gradletest.steam;

import com.google.common.base.Preconditions;

import java.time.LocalDate;

public class SteamWishlistEmail {

    private final LocalDate emailDate;
    private final String steamGameName;
    private final String steamGameUrl;

    public SteamWishlistEmail(final LocalDate emailDate, final String steamGameName, final String steamGameUrl) {
        Preconditions.checkNotNull(emailDate);
        Preconditions.checkNotNull(steamGameName);
        Preconditions.checkNotNull(steamGameUrl);

        this.emailDate = emailDate;
        this.steamGameName = steamGameName;
        this.steamGameUrl = steamGameUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SteamWishlistEmail that = (SteamWishlistEmail) o;

        return emailDate.equals(that.emailDate) && steamGameName.equals(that.steamGameName) && steamGameUrl.equals(that.steamGameUrl);

    }

    @Override
    public int hashCode() {
        int result = emailDate.hashCode();
        result = 31 * result + steamGameName.hashCode();
        result = 31 * result + steamGameUrl.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SteamWishlistEmail{" +
                "emailDate=" + emailDate +
                ", steamGameName='" + steamGameName + '\'' +
                ", steamGameUrl='" + steamGameUrl + '\'' +
                '}';
    }
}
