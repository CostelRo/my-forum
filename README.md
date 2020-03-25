# MyForum (Java REST learning project)

An extremely simple forum engine.
The intended functionaly is presented below.


**User**
 
1. *Register*: users should register with unique username and e-mail, plus their password, first name and last name;
2. *Search*: a registered user should be able to search for other users by username, first name or last name
3. *Follow*: a registered user should be able to follow another user (and gain access to a feed with their posts)
4. *Unfollow*: a registered user should be able to un-follow another user (no more access to posts feed from that user)
5. *Unregister*: a registered user should be able to remove themselves and all their previous activity
 
 
**Post**
 
1. *Add post*: a registered user should be able to post a message
2. *Get own posts*: return all posts added by the current user, including a filter for posts newer than a timestamp
3. *Get feed*: return all posts added by users followed by the current user
4. *Delete post*: remove a post previously published by the active user


**Like**
 
1. *Like post*: mark an existing post with a "like"; the post owner will receive the list of all likes for each post
2. *Remove like*
3. *Cascade remove like*: when a post is deleted, all its likes are deleted too


**Reply**
 
1. *Add post reply*: reply to existing posts or to other replies
2. *Cascade remove reply*: when a parent post is deleted, all its replies are removed too
