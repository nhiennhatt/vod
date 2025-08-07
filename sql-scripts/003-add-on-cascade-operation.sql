USE vod;

-- Drop existing foreign key constraints
ALTER TABLE user_informs DROP FOREIGN KEY user_informs_ibfk_1;
ALTER TABLE videos DROP FOREIGN KEY videos_ibfk_1;
ALTER TABLE subscribes DROP FOREIGN KEY subscribes_ibfk_1;
ALTER TABLE subscribes DROP FOREIGN KEY subscribes_ibfk_2;
ALTER TABLE likes DROP FOREIGN KEY likes_ibfk_1;
ALTER TABLE likes DROP FOREIGN KEY likes_ibfk_2;
ALTER TABLE histories DROP FOREIGN KEY histories_ibfk_1;
ALTER TABLE histories DROP FOREIGN KEY histories_ibfk_2;
ALTER TABLE video_categories DROP FOREIGN KEY video_categories_ibfk_1;
ALTER TABLE video_categories DROP FOREIGN KEY video_categories_ibfk_2;
ALTER TABLE comments DROP FOREIGN KEY comments_ibfk_1;
ALTER TABLE comments DROP FOREIGN KEY comments_ibfk_2;
ALTER TABLE comments DROP FOREIGN KEY comments_ibfk_3;
ALTER TABLE video_violations DROP FOREIGN KEY video_violations_ibfk_1;
ALTER TABLE playlists DROP FOREIGN KEY playlists_ibfk_1;
ALTER TABLE video_playlist DROP FOREIGN KEY video_playlist_ibfk_1;
ALTER TABLE video_playlist DROP FOREIGN KEY video_playlist_ibfk_2;
ALTER TABLE notifications DROP FOREIGN KEY notifications_ibfk_1;

-- Recreate foreign key constraints with ON DELETE CASCADE
ALTER TABLE user_informs
    ADD CONSTRAINT user_informs_ibfk_1
    FOREIGN KEY (id) REFERENCES users (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE videos
    ADD CONSTRAINT videos_ibfk_1
    FOREIGN KEY (user_id) REFERENCES users (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE subscribes
    ADD CONSTRAINT subscribes_ibfk_1
    FOREIGN KEY (source_user) REFERENCES users (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE subscribes
    ADD CONSTRAINT subscribes_ibfk_2
    FOREIGN KEY (dest_user) REFERENCES users (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE likes
    ADD CONSTRAINT likes_ibfk_1
    FOREIGN KEY (user_id) REFERENCES users (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE likes
    ADD CONSTRAINT likes_ibfk_2
    FOREIGN KEY (video_id) REFERENCES videos (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE histories
    ADD CONSTRAINT histories_ibfk_1
    FOREIGN KEY (user_id) REFERENCES users (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE histories
    ADD CONSTRAINT histories_ibfk_2
    FOREIGN KEY (video_id) REFERENCES videos (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE video_categories
    ADD CONSTRAINT video_categories_ibfk_1
    FOREIGN KEY (category_id) REFERENCES categories (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE video_categories
    ADD CONSTRAINT video_categories_ibfk_2
    FOREIGN KEY (video_id) REFERENCES videos (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE comments
    ADD CONSTRAINT comments_ibfk_1
    FOREIGN KEY (user_id) REFERENCES users (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE comments
    ADD CONSTRAINT comments_ibfk_2
    FOREIGN KEY (video_id) REFERENCES videos (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE comments
    ADD CONSTRAINT comments_ibfk_3
    FOREIGN KEY (parent_comment_id) REFERENCES comments (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE video_violations
    ADD CONSTRAINT video_violations_ibfk_1
    FOREIGN KEY (video_id) REFERENCES videos (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE playlists
    ADD CONSTRAINT playlists_ibfk_1
    FOREIGN KEY (owner_id) REFERENCES users (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE video_playlist
    ADD CONSTRAINT video_playlist_ibfk_1
    FOREIGN KEY (playlist_id) REFERENCES playlists (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE video_playlist
    ADD CONSTRAINT video_playlist_ibfk_2
    FOREIGN KEY (video_id) REFERENCES videos (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE notifications
    ADD CONSTRAINT notifications_ibfk_1
    FOREIGN KEY (to_user) REFERENCES users (id)
    ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE videos modify column thumbnail varchar(55) not null;
ALTER TABLE videos modify column file_name varchar(55) not null;