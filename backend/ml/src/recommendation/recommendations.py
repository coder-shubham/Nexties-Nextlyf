import pandas as pd
import numpy as np
import logging
from recommendation.friends.friend_recommendation import convert_user_to_row

room_recommendation_df = pd.read_csv('../data/room_recommendation.csv')
# print(room_recommendation_df.head())
friends_recommendation_df = pd.read_csv('../data/friends_recommendation.csv')
# print(friends_recommendation_df.head())
bookings_df = pd.read_csv('../data/room_bookings.csv')
# print(bookings_df.head())

def recommend_room(user_dict, room_recommendation_df):
    # user_row = convert_user_to_row(user_dict)
    room_recommendation_df['score'] = 0.0
    
    # get similar users
    friends = recommend_friends(user_dict, friends_recommendation_df.copy())

    # get past bookings
    user_room_booking_ids = bookings_df.loc[bookings_df['userId'].isin([user_dict['userId'], *friends]), 'roomId'].values
    if len(user_room_booking_ids) != 0:
        for room_id in user_room_booking_ids:
            # print(room_recommendation_df.head())
            # room_id = row['roomId']
            room_row = room_recommendation_df.loc[room_recommendation_df['__id__'] == room_id].values[0]
            
            # room_recommendation_df['score'] = room_recommendation_df['score'].astype(float) + room_recommendation_df.apply(lambda x: float(x['score']) + sum(x[1:] * room_row[1:]) / len(room_row[1:]), axis=1).astype(float)
            room_recommendation_df['score'] += room_recommendation_df.apply(
                lambda x: (sum(x[1:] * room_row[1:]) / len(room_row[1:])), axis=1
            )
        # room_recommendation_df = room_recommendation_df.loc[room_recommendation_df['id'] != user_dict['userId']]
        # room_recommendation_df['score'] = room_recommendation_df.apply(lambda x: sum(x[1:] * user_row[1:]), axis=1)

    return room_recommendation_df.sort_values(by='score', ascending=False).loc[:5, '__id__'].tolist() #.head(5)


def recommend_friends(user_dict, friends_recommendation_df):
    user_row = convert_user_to_row(user_dict)
    # logging.info(f"User row: {user_row}")
    # logging.info(f"Friends recommendation df: {friends_recommendation_df.head()}")
    filtered_friends_recommendation_df = friends_recommendation_df.loc[friends_recommendation_df['__id__'] != user_dict['userId']].copy()
    filtered_friends_recommendation_df.loc[:, 'score'] = filtered_friends_recommendation_df.apply(lambda x: sum(x[1:] * user_row[1:]) / len(user_row[1:]), axis=1)
    return filtered_friends_recommendation_df.sort_values(by='score', ascending=False).loc[:5, '__id__'].tolist() #.head(5)

def recommend(user_dict, recommendation_type):
    if recommendation_type == 'rooms':
        return recommend_room(user_dict, room_recommendation_df.copy())
    elif recommendation_type == 'friends':
        return recommend_friends(user_dict, friends_recommendation_df.copy())
    else:
        return []