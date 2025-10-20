create database if not exists vod character set utf8mb4 collate utf8mb4_unicode_ci;

use vod;

CREATE TABLE `users`
(
    `id`         INTEGER AUTO_INCREMENT UNIQUE                      NOT NULL,
    `username`   VARCHAR(255)                                       NOT NULL UNIQUE ,
    `email`      VARCHAR(255)                                       NOT NULL UNIQUE ,
    `password`   VARCHAR(255)                                       NOT NULL,
    `status`     ENUM ('ACTIVE', 'INACTIVE', 'DELETED')             NOT NULL DEFAULT 'ACTIVE',
    `isVerified` BOOLEAN                                            NOT NULL DEFAULT false,
    `role`       ENUM ('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR') NOT NULL DEFAULT 'ROLE_USER',
    `created_on` TIMESTAMP                                          NOT NULL,
    `updated_on` TIMESTAMP                                          NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE INDEX `users_index_0`
    ON `users` (`username`, `email`);
CREATE TABLE `user_informs`
(
    `id`            INTEGER   NOT NULL UNIQUE,
    `first_name`    VARCHAR(255),
    `last_name`     VARCHAR(255),
    `middle_name`   VARCHAR(255),
    `date_of_birth` DATE,
    `description`   TEXT,
    `avatar`        VARCHAR(45) unique,
    `cover_img`     VARCHAR(45) unique,
    `created_on`    TIMESTAMP NOT NULL,
    `updated_on`    TIMESTAMP NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE `videos`
(
    `id`          INTEGER AUTO_INCREMENT UNIQUE                                   NOT NULL,
    `uid`         BINARY(16) UNIQUE                                               NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    `user_id`     INTEGER                                                         NOT NULL,
    `title`        VARCHAR(255)                                                    NOT NULL,
    `thumbnail`   VARCHAR(45)                                                     NOT NULL,
    `description` TEXT                                                            NOT NULL,
    `file_name`   VARCHAR(45)                                                     NOT NULL,
    `status`      ENUM ('PROCESSING', 'ACTIVE', 'INACTIVE', 'VIOLATED', 'FAILED') NOT NULL DEFAULT 'ACTIVE',
    `privacy`     ENUM ('PUBLIC', 'PRIVATE', 'LIMITED')                           NOT NULL,
    `created_on`  TIMESTAMP                                                       NOT NULL,
    `updated_on`  TIMESTAMP                                                       NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE INDEX `videos_index_0`
    ON `videos` (`user_id`);
CREATE TABLE `subscribes`
(
    `id`          INTEGER AUTO_INCREMENT UNIQUE NOT NULL,
    `uid`         BINARY(16) UNIQUE             NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    `source_user` INTEGER                       NOT NULL,
    `dest_user`   INTEGER                       NOT NULL,
    `created_on`  TIMESTAMP                     NOT NULL,
    `updated_on`  TIMESTAMP                     NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE INDEX `subscribes_index_0`
    ON `subscribes` (`source_user`, `dest_user`);
CREATE TABLE `likes`
(
    `id`         INTEGER AUTO_INCREMENT UNIQUE NOT NULL,
    `uid`        BINARY(16) UNIQUE             NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    `user_id`    INTEGER                       NOT NULL,
    `video_id`   INTEGER                       NOT NULL,
    `created_on` TIMESTAMP                     NOT NULL,
    `updated_on` TIMESTAMP                     NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE INDEX `likes_index_0`
    ON `likes` (`user_id`, `video_id`);
CREATE TABLE `histories`
(
    `id`         INTEGER AUTO_INCREMENT UNIQUE NOT NULL,
    `uid`        BINARY(16) UNIQUE             NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    `user_id`    INTEGER                       NOT NULL,
    `video_id`   INTEGER                       NOT NULL,
    `created_on` TIMESTAMP                     NOT NULL,
    `updated_on` TIMESTAMP                     NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE INDEX `histories_index_0`
    ON `histories` (`user_id`, `video_id`);
CREATE TABLE `categories`
(
    `id`   INTEGER      NOT NULL AUTO_INCREMENT UNIQUE,
    `name` VARCHAR(255) NOT NULL,
    `slug` VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);


CREATE TABLE `video_categories`
(
    `id`          INTEGER AUTO_INCREMENT UNIQUE NOT NULL,
    `uid`         BINARY(16) UNIQUE             NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    `category_id` INTEGER                       NOT NULL,
    `video_id`    INTEGER                       NOT NULL,
    `created_on`  TIMESTAMP                     NOT NULL,
    `updated_on`  TIMESTAMP                     NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE INDEX `video_categories_index_0`
    ON `video_categories` (`video_id`, `category_id`);
CREATE TABLE `comments`
(
    `id`                INTEGER AUTO_INCREMENT UNIQUE            NOT NULL,
    `uid`               BINARY(16) UNIQUE                        NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    `content`           TEXT                                     NOT NULL,
    `user_id`           INTEGER                                  NOT NULL,
    `video_id`          INTEGER                                  NOT NULL,
    `parent_comment_id` INTEGER,
    `status`            ENUM ('ACTIVE', 'INACTIVE', 'VIOLATING') NOT NULL,
    `created_on`        TIMESTAMP                                NOT NULL,
    `updated_on`        TIMESTAMP                                NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE INDEX `comments_index_0`
    ON `comments` (`video_id`, `user_id`, `parent_comment_id`);
CREATE TABLE `video_violations`
(
    `id`         INTEGER      NOT NULL AUTO_INCREMENT UNIQUE,
    `title`      VARCHAR(255) NOT NULL,
    `content`    TEXT         NOT NULL,
    `created_on` TIMESTAMP    NOT NULL,
    `updated_on` TIMESTAMP    NOT NULL,
    `moderator`  VARCHAR(255) NOT NULL,
    `video_id`   INTEGER      NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE INDEX `video_violations_index_0`
    ON `video_violations` (`video_id`);
CREATE TABLE `playlists`
(
    `id`       INTEGER AUTO_INCREMENT UNIQUE NOT NULL,
    `uid`      BINARY(16) UNIQUE             NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    `title`    VARCHAR(255)                  NOT NULL,
    `owner_id` INTEGER                       NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE INDEX `playlists_index_0`
    ON `playlists` (`owner_id`);
CREATE TABLE `video_playlist`
(
    `id`          INTEGER AUTO_INCREMENT UNIQUE NOT NULL,
    `uid`         BINARY(16) UNIQUE             NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    `playlist_id` INTEGER                       NOT NULL,
    `video_id`    INTEGER                       NOT NULL,
    `created_on`  TIMESTAMP                     NOT NULL,
    `updated_on`  TIMESTAMP                     NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE INDEX `video_playlist_index_0`
    ON `video_playlist` (`playlist_id`, `video_id`);
CREATE TABLE `notifications`
(
    `id`         INTEGER AUTO_INCREMENT UNIQUE     NOT NULL,
    `uid`        BINARY(16) UNIQUE                 NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    `title`      VARCHAR(255)                      NOT NULL,
    `content`    TEXT                              NOT NULL,
    `next`       VARCHAR(255),
    `to_user`    INTEGER                           NOT NULL,
    `created_on` TIMESTAMP                         NOT NULL,
    `updated_on` TIMESTAMP                         NOT NULL,
    `status`     ENUM ('SEND', 'SEEN', 'INACTIVE') NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE INDEX `notifications_index_0`
    ON `notifications` (`to_user`);
ALTER TABLE `user_informs`
    ADD FOREIGN KEY (`id`) REFERENCES `users` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `videos`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `subscribes`
    ADD FOREIGN KEY (`source_user`) REFERENCES `users` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `subscribes`
    ADD FOREIGN KEY (`dest_user`) REFERENCES `users` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `likes`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `likes`
    ADD FOREIGN KEY (`video_id`) REFERENCES `videos` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `histories`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `histories`
    ADD FOREIGN KEY (`video_id`) REFERENCES `videos` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `video_categories`
    ADD FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `video_categories`
    ADD FOREIGN KEY (`video_id`) REFERENCES `videos` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `comments`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `comments`
    ADD FOREIGN KEY (`video_id`) REFERENCES `videos` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `comments`
    ADD FOREIGN KEY (`parent_comment_id`) REFERENCES `comments` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `video_violations`
    ADD FOREIGN KEY (`video_id`) REFERENCES `videos` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `playlists`
    ADD FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `video_playlist`
    ADD FOREIGN KEY (`playlist_id`) REFERENCES `playlists` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `video_playlist`
    ADD FOREIGN KEY (`video_id`) REFERENCES `videos` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `notifications`
    ADD FOREIGN KEY (`to_user`) REFERENCES `users` (`id`)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
