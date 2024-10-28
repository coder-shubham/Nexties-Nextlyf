import json
import logging
import pandas as pd

filtered_friends_df = pd.read_csv('../data/friends_recommendation.csv')
age_breaks = [18, 25, 35, 45, 60]

def convert_user_to_row(user_dict):
    logging.info(f"User dict: {user_dict}")
    # convert user to filtered friends row format
    user_df = pd.DataFrame(user_dict, index=[0])
    user_df = user_df.replace('Yes', 1).replace('No', 0)
    logging.info(f"User df: {user_df}")
    user_df['year'] = pd.to_datetime(user_df['dob'], format='%d/%m/%y')
    user_df['age'] = 2024 - user_df['year'].dt.year

    user_df.loc[:, list(set(filtered_friends_df.columns) - set(user_df.columns))] = 0

    # age group
    for i in range(1, len(age_breaks)):
        user_df['age_' + str(age_breaks[i-1]) + '_' + str(age_breaks[i])] = user_df['age'].apply(lambda x: 1 if x > age_breaks[i-1] and x <= age_breaks[i] else 0)

    # fill in the extra columns
    for value in user_df['fieldsOfInterest'].values[0].split(', '):
        user_df.at[0, value] = 1
    for value in user_df['hobbies'].values[0].split(', '):
        user_df.at[0, value] = 1
    for value in user_df['gender']:
        user_df.at[0, 'gender_' + value] = 1
    for value in user_df['country']:
        user_df.at[0, 'country_' + value] = 1
    # for value in user_df['city'].values[0]:
    #     user_df.at[0, 'city_' + value] = 1
    for value in user_df['language']:
        user_df.at[0, 'language_' + value] = 1
    for value in user_df['profession']:
        user_df.at[0, 'profession_' + value] = 1

    # user_df['userId', 'age_18_25', 'age_25_35', 'age_35_45', 'age_45_60', 'gender_male', 'gender_female', 'gender_trans', 'country_USA', 'country_Canada', 'country_UK', 'country_Australia', 'country_Germany', 'country_France', 'country_India', 'country_Brazil', 'country_Japan', 'country_South Korea', 'country_Mexico', 'country_Italy', 'country_Netherlands', 'country_Sweden', 'country_South Africa', 'country_Spain', 'country_Russia', 'country_Singapore', 'country_Argentina', 'country_China', 'country_Saudi Arabia', 'country_Ghana', 'country_UAE', 'country_United Arab Emirates', 'country_New Zealand', 'country_Malaysia', 'country_Indonesia', 'city_New York', 'city_Toronto', 'city_London', 'city_Sydney', 'city_Berlin', 'city_Paris', 'city_Mumbai', 'city_São Paulo', 'city_Tokyo', 'city_Seoul', 'city_Mexico City', 'city_Rome', 'city_Amsterdam', 'city_Stockholm', 'city_Cape Town', 'city_Barcelona', 'city_Moscow', 'city_Singapore', 'city_Buenos Aires', 'city_Beijing', 'city_Madrid', 'city_Riyadh', 'city_Accra', 'city_Dubai', 'city_Shanghai', 'city_Auckland', 'city_Melbourne', 'city_Kuala Lumpur', 'city_Birmingham', 'city_Jakarta', 'city_Los Angeles', 'language_English', 'language_German', 'language_French', 'language_Hindi', 'language_Portuguese', 'language_Japanese', 'language_Korean', 'language_Spanish', 'language_Italian', 'language_Dutch', 'language_Swedish', 'language_Afrikaans', 'language_Russian', 'language_Mandarin', 'language_Arabic', 'language_Chinese', 'language_Malay', 'language_Indonesian', 'profession_Technology & IT', 'profession_Healthcare', 'profession_Education', 'profession_Arts & Entertainment', 'profession_Business & Finance', 'profession_Media & Communication', 'profession_Science & Research', 'profession_Retail & E-commerce', 'profession_Hospitality & Tourism', 'profession_Legal', 'Technology', 'Gaming', 'Finance', 'Healthcare', 'Fitness & Wellness', 'Education', 'Social Impact', 'Creative Arts', 'Media & Journalism', 'Business', 'Marketing', 'Science', 'Engineering', 'Environment', 'Fashion & Design', 'Travel & Tourism', 'Real Estate', 'Media', 'Retail', 'Government', 'Fitness', 'Wellness', 'Cryptocurrency', 'Music', 'Food & Culinary', 'Reading', 'Traveling', 'Fitness/Exercise', 'Cooking/Baking', 'Yoga/Meditation', 'Volunteering', 'Learning New Skills', 'Photography', 'Art & Craft', 'Collecting', 'Socializing', 'Watching Movies/TV Shows', 'Dancing', 'Hiking/Camping', 'Gardening', 'Playing Video Games', 'Writing', 'Playing Sports', 'Board Games or Puzzles', 'Volunteer'].head()
    return user_df.loc[:, list(filtered_friends_df.columns)].values[0]
