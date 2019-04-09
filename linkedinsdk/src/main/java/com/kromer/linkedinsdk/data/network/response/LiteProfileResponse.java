package com.kromer.linkedinsdk.data.network.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Kerollos Kromer on 07-Apr-19.
 */
public class LiteProfileResponse {

  /**
   * firstName : {"localized":{"en_US":"Kerollos"},"preferredLocale":{"country":"US","language":"en"}}
   * lastName : {"localized":{"en_US":"Kromer"},"preferredLocale":{"country":"US","language":"en"}}
   * profilePicture : {"displayImage":"urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg","displayImage~":{"elements":[{"artifact":"urn:li:digitalmediaMediaArtifact:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_100_100)","authorizationMethod":"PUBLIC","data":{"com.linkedin.digitalmedia.mediaartifact.StillImage":{"storageSize":{"width":100,"height":100},"storageAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"},"mediaType":"image/jpeg","rawCodecSpec":{"name":"jpeg","type":"image"},"displaySize":{"uom":"PX","width":100,"height":100},"displayAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"}}},"identifiers":[{"identifier":"https://media.licdn.com/dms/image/C4E03AQEv4uYufVXwmg/profile-displayphoto-shrink_100_100/0?e=1560384000&v=beta&t=R3JkadOwalu8cHfKSnxD_4DvIUVicv3cqxK8fWZz5Cs","file":"urn:li:digitalmediaFile:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_100_100,0)","index":0,"mediaType":"image/jpeg","identifierType":"EXTERNAL_URL","identifierExpiresInSeconds":1560384000}]},{"artifact":"urn:li:digitalmediaMediaArtifact:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_200_200)","authorizationMethod":"PUBLIC","data":{"com.linkedin.digitalmedia.mediaartifact.StillImage":{"storageSize":{"width":200,"height":200},"storageAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"},"mediaType":"image/jpeg","rawCodecSpec":{"name":"jpeg","type":"image"},"displaySize":{"uom":"PX","width":200,"height":200},"displayAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"}}},"identifiers":[{"identifier":"https://media.licdn.com/dms/image/C4E03AQEv4uYufVXwmg/profile-displayphoto-shrink_200_200/0?e=1560384000&v=beta&t=9I6e_3N30_5phsggw45pTbVlB5Eu1m3KVKuWIE4Oncc","file":"urn:li:digitalmediaFile:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_200_200,0)","index":0,"mediaType":"image/jpeg","identifierType":"EXTERNAL_URL","identifierExpiresInSeconds":1560384000}]},{"artifact":"urn:li:digitalmediaMediaArtifact:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_400_400)","authorizationMethod":"PUBLIC","data":{"com.linkedin.digitalmedia.mediaartifact.StillImage":{"storageSize":{"width":400,"height":400},"storageAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"},"mediaType":"image/jpeg","rawCodecSpec":{"name":"jpeg","type":"image"},"displaySize":{"uom":"PX","width":400,"height":400},"displayAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"}}},"identifiers":[{"identifier":"https://media.licdn.com/dms/image/C4E03AQEv4uYufVXwmg/profile-displayphoto-shrink_400_400/0?e=1560384000&v=beta&t=LufQHrexZnP1mt36FI1-eGVm8JlklSyNqDStas1Th9M","file":"urn:li:digitalmediaFile:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_400_400,0)","index":0,"mediaType":"image/jpeg","identifierType":"EXTERNAL_URL","identifierExpiresInSeconds":1560384000}]},{"artifact":"urn:li:digitalmediaMediaArtifact:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_800_800)","authorizationMethod":"PUBLIC","data":{"com.linkedin.digitalmedia.mediaartifact.StillImage":{"storageSize":{"width":800,"height":800},"storageAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"},"mediaType":"image/jpeg","rawCodecSpec":{"name":"jpeg","type":"image"},"displaySize":{"uom":"PX","width":800,"height":800},"displayAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"}}},"identifiers":[{"identifier":"https://media.licdn.com/dms/image/C4E03AQEv4uYufVXwmg/profile-displayphoto-shrink_800_800/0?e=1560384000&v=beta&t=nBGwCasvLdIl8NcfIVVTp_qkGMJUgBXTf75lAO8N0i4","file":"urn:li:digitalmediaFile:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_800_800,0)","index":0,"mediaType":"image/jpeg","identifierType":"EXTERNAL_URL","identifierExpiresInSeconds":1560384000}]}],"paging":{"count":10,"start":0,"links":[]}}}
   * id : ziJck-Yo7j
   */

  private String id;
  private NameBean firstName;
  private NameBean lastName;
  private ProfilePictureBean profilePicture;

  public String getId() {
    return id;
  }

  public String getFirstName() {
    return firstName != null
        ? firstName.getLocalized().getEn_US()
        : "";
  }

  public String getLastName() {
    return lastName != null
        ? lastName.getLocalized().getEn_US()
        : "";
  }

  public String getProfilePicture() {
    return (profilePicture != null
        && profilePicture.getDisplayImage().getElements() != null
        && profilePicture.getDisplayImage().getElements().size() > 1)
        ? profilePicture.getDisplayImage().getElements().get(1)
        .getIdentifiers().get(0).getIdentifier()
        : "";
  }

  private static class NameBean {
    /**
     * localized : {"en_US":"Kerollos"}
     * preferredLocale : {"country":"US","language":"en"}
     */

    private LocalizedBean localized;
    private PreferredLocaleBean preferredLocale;

    private LocalizedBean getLocalized() {
      return localized;
    }

    private PreferredLocaleBean getPreferredLocale() {
      return preferredLocale;
    }

    private static class LocalizedBean {
      /**
       * en_US : Kerollos
       */

      private String en_US;

      private String getEn_US() {
        return en_US;
      }
    }

    private static class PreferredLocaleBean {
      /**
       * country : US
       * language : en
       */

      private String country;
      private String language;

      private String getCountry() {
        return country;
      }

      private String getLanguage() {
        return language;
      }
    }
  }

  private static class ProfilePictureBean {
    /**
     * displayImage : urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg
     * displayImage~ : {"elements":[{"artifact":"urn:li:digitalmediaMediaArtifact:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_100_100)","authorizationMethod":"PUBLIC","data":{"com.linkedin.digitalmedia.mediaartifact.StillImage":{"storageSize":{"width":100,"height":100},"storageAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"},"mediaType":"image/jpeg","rawCodecSpec":{"name":"jpeg","type":"image"},"displaySize":{"uom":"PX","width":100,"height":100},"displayAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"}}},"identifiers":[{"identifier":"https://media.licdn.com/dms/image/C4E03AQEv4uYufVXwmg/profile-displayphoto-shrink_100_100/0?e=1560384000&v=beta&t=R3JkadOwalu8cHfKSnxD_4DvIUVicv3cqxK8fWZz5Cs","file":"urn:li:digitalmediaFile:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_100_100,0)","index":0,"mediaType":"image/jpeg","identifierType":"EXTERNAL_URL","identifierExpiresInSeconds":1560384000}]},{"artifact":"urn:li:digitalmediaMediaArtifact:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_200_200)","authorizationMethod":"PUBLIC","data":{"com.linkedin.digitalmedia.mediaartifact.StillImage":{"storageSize":{"width":200,"height":200},"storageAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"},"mediaType":"image/jpeg","rawCodecSpec":{"name":"jpeg","type":"image"},"displaySize":{"uom":"PX","width":200,"height":200},"displayAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"}}},"identifiers":[{"identifier":"https://media.licdn.com/dms/image/C4E03AQEv4uYufVXwmg/profile-displayphoto-shrink_200_200/0?e=1560384000&v=beta&t=9I6e_3N30_5phsggw45pTbVlB5Eu1m3KVKuWIE4Oncc","file":"urn:li:digitalmediaFile:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_200_200,0)","index":0,"mediaType":"image/jpeg","identifierType":"EXTERNAL_URL","identifierExpiresInSeconds":1560384000}]},{"artifact":"urn:li:digitalmediaMediaArtifact:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_400_400)","authorizationMethod":"PUBLIC","data":{"com.linkedin.digitalmedia.mediaartifact.StillImage":{"storageSize":{"width":400,"height":400},"storageAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"},"mediaType":"image/jpeg","rawCodecSpec":{"name":"jpeg","type":"image"},"displaySize":{"uom":"PX","width":400,"height":400},"displayAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"}}},"identifiers":[{"identifier":"https://media.licdn.com/dms/image/C4E03AQEv4uYufVXwmg/profile-displayphoto-shrink_400_400/0?e=1560384000&v=beta&t=LufQHrexZnP1mt36FI1-eGVm8JlklSyNqDStas1Th9M","file":"urn:li:digitalmediaFile:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_400_400,0)","index":0,"mediaType":"image/jpeg","identifierType":"EXTERNAL_URL","identifierExpiresInSeconds":1560384000}]},{"artifact":"urn:li:digitalmediaMediaArtifact:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_800_800)","authorizationMethod":"PUBLIC","data":{"com.linkedin.digitalmedia.mediaartifact.StillImage":{"storageSize":{"width":800,"height":800},"storageAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"},"mediaType":"image/jpeg","rawCodecSpec":{"name":"jpeg","type":"image"},"displaySize":{"uom":"PX","width":800,"height":800},"displayAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"}}},"identifiers":[{"identifier":"https://media.licdn.com/dms/image/C4E03AQEv4uYufVXwmg/profile-displayphoto-shrink_800_800/0?e=1560384000&v=beta&t=nBGwCasvLdIl8NcfIVVTp_qkGMJUgBXTf75lAO8N0i4","file":"urn:li:digitalmediaFile:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_800_800,0)","index":0,"mediaType":"image/jpeg","identifierType":"EXTERNAL_URL","identifierExpiresInSeconds":1560384000}]}],"paging":{"count":10,"start":0,"links":[]}}
     */

    @SerializedName("displayImage~")
    private DisplayImage displayImage;

    private DisplayImage getDisplayImage() {
      return displayImage;
    }

    private static class DisplayImage {

      private List<ElementsBean> elements;

      private List<ElementsBean> getElements() {
        return elements;
      }

      private static class ElementsBean {
        /**
         * artifact : urn:li:digitalmediaMediaArtifact:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_100_100)
         * authorizationMethod : PUBLIC
         * data : {"com.linkedin.digitalmedia.mediaartifact.StillImage":{"storageSize":{"width":100,"height":100},"storageAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"},"mediaType":"image/jpeg","rawCodecSpec":{"name":"jpeg","type":"image"},"displaySize":{"uom":"PX","width":100,"height":100},"displayAspectRatio":{"widthAspect":1,"heightAspect":1,"formatted":"1.00:1.00"}}}
         * identifiers : [{"identifier":"https://media.licdn.com/dms/image/C4E03AQEv4uYufVXwmg/profile-displayphoto-shrink_100_100/0?e=1560384000&v=beta&t=R3JkadOwalu8cHfKSnxD_4DvIUVicv3cqxK8fWZz5Cs","file":"urn:li:digitalmediaFile:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_100_100,0)","index":0,"mediaType":"image/jpeg","identifierType":"EXTERNAL_URL","identifierExpiresInSeconds":1560384000}]
         */

        private List<IdentifiersBean> identifiers;

        private List<IdentifiersBean> getIdentifiers() {
          return identifiers;
        }

        private static class IdentifiersBean {
          /**
           * identifier : https://media.licdn.com/dms/image/C4E03AQEv4uYufVXwmg/profile-displayphoto-shrink_100_100/0?e=1560384000&v=beta&t=R3JkadOwalu8cHfKSnxD_4DvIUVicv3cqxK8fWZz5Cs
           * file : urn:li:digitalmediaFile:(urn:li:digitalmediaAsset:C4E03AQEv4uYufVXwmg,urn:li:digitalmediaMediaArtifactClass:profile-displayphoto-shrink_100_100,0)
           * index : 0
           * mediaType : image/jpeg
           * identifierType : EXTERNAL_URL
           * identifierExpiresInSeconds : 1560384000
           */

          private String identifier;

          private String getIdentifier() {
            return identifier;
          }
        }
      }
    }
  }
}