# Random Recipe!

## Table of Contents

1. [App Overview](#App-Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
1. [Build Notes](#Build-Notes)

## App Overview

### Description 

**Cook Like a Chef! is an app where users are able to scroll through different recipes and get instructions for the recipes of interest. This app allows for ease of access and a quick way to find new recipes!**

### App Evaluation

<!-- Evaluation of your app across the following attributes -->

- **Category:**: Food/Home

- **Mobile**: This app would mainly provide ease of access because if someone is cooking, having a whole laptop out for a recipe might be a lot. In addition, people can quickly see multiple ideas.

- **Story**: When walking around the grocery store, it would be easy to just scroll through and find something quick to make. It would be easy and efficient for people to use.

- **Market**: The market is large because everyone needs food, so everyone could use this. It's especially convenient for people who are on the go (i.e. office workers, etc) so they can look for recipes while commuting. 

- **Habit**: This is very habit forming because people can endlessly scroll for recipes and can save the receipes that they like. People need recipes for food everyday, so this would be used often.

- **Scope**: A stripped down version would be having a recycler view with endless scroll of different recipes (click on the recipe and the directions will pop up in a page) and a save function where people can look at the receipes they saved. This should be fine to complete by the deadline, especially by using previous projects for reference. This is clearly defined as a recipe app.

## Product Spec

### 1. User Features (Required and Optional)

Required Features:

- [x] **Recycler view of recipes**
- [x] **Can click on recipe and information + directions appear**
- [x] **User can save recipe**

Stretch Features:

- [x] **User can search for a recipe**
- [x] **filter by main ingredient**
- [x] **filter by category**

### 2. Chosen API(s)


**list first API endpoint here**
https://www.themealdb.com/api.php


**list associated required feature here**

- get recipe 
www.themealdb.com/api/json/v1/1/random.php
- filter by category
www.themealdb.com/api/json/v1/1/categories.php
**(stretch)**
- filter by ingredient
www.themealdb.com/api/json/v1/1/filter.php?i=chicken_breast
- user can search meal/recipe by name 
www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata

### 3. User Interaction

Required Feature

- **scroll**
  - => **Can scroll through all the recipes**

- **select**
  - => **Can select recipes and get their information**

- **bookmark**
  - => **See the list of saved recipes**
  
 - **search** 
   - => **search for a specific recipe**


## Wireframes

<!-- Add picture of your hand sketched wireframes in this section -->
<img src="https://i.imgur.com/oOvn9Mk.jpeg" width=600>
<img src="https://i.imgur.com/DNYvOPR.jpeg" width=600>

### [BONUS] Digital Wireframes & Mockups

![wireframes 1](https://hackmd.io/_uploads/Hk-M98OlR.png)

![wireframe 2](https://hackmd.io/_uploads/rJMaYLdeC.jpg)

![wireframe 3](https://hackmd.io/_uploads/HyBjqI_l0.png)


## Build Notes

In this first step we created the digital wireframes that show what the app will look like.   
We started making the wireframes. (From the layout of our bonus digital wireframes), the first
one is the main recycler view page. The second one is the page if we click on the recipe.
The last one is the saved recipes page.

For Milestone 2, include **2+ Videos/GIFs** of the build process here!
<img src='https://i.imgur.com/yQq2seb.gif' title='Video Demo 1' width='' alt='Video Demo 1' />
<img src='https://i.imgur.com/47M5fn2.gif' title='Video Demo 2' width='' alt='Video Demo 2' />
<img src='https://i.imgur.com/qQtm8pr.gif' title='Video Demo 3' width='' alt='Video Demo 3' />


## License

Copyright **2024** **Erlisja Kore, Vivian Ha, Nobel Menghis, Tetyana Matsegora**

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
