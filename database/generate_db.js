const generate_restaurants = (num) => {
  const restaurants = {};
  for (let i = 1; i <= num; i++) {
    const r = `r${i}`;
    restaurants[r] = {
      id: r,
      name: "Pizza Town",
      imageUrl:
        "https://firebasestorage.googleapis.com/v0/b/wagba-a12cb.appspot.com/o/dish_images%2FClassic-Pepperoni-p1.png?alt=media&token=215225fb-dee9-4b0b-bb38-05bb34e301df",
      address: "1 Abdo Basha, Cairo",
      description: "The best pizza in the world. You name the taste we get it.",
    };
  }
  return restaurants;
};

const generate_dishes = (num) => {
  const dishes = {};
  for (let i = 1; i <= num; i++) {
    const r = `r${i}`;
    dishes[r] = {
      d1: {
        id: "d1",
        name: "Classic Pepperoni",
        imageUrl:
          "https://firebasestorage.googleapis.com/v0/b/wagba-a12cb.appspot.com/o/dish_images%2FClassic-Pepperoni-p1.png?alt=media&token=215225fb-dee9-4b0b-bb38-05bb34e301df",
        price: 109.0,
        description:
          "One of our most authentic classics. Olives, pepperoni, and the rest.",
        available: true,
      },
      d2: {
        id: "d2",
        name: "Chicken Supreme",
        imageUrl:
          "https://firebasestorage.googleapis.com/v0/b/wagba-a12cb.appspot.com/o/dish_images%2FChicken-Supreme-p1.png?alt=media&token=15b1b825-9995-4e02-bafe-97ad98f88842",
        price: 122.0,
        description:
          "One of our most authentic classics. Olives, chicken, and the rest.",
        available: false,
      },
      d3: {
        id: "d3",
        name: "Veggies",
        imageUrl:
          "https://firebasestorage.googleapis.com/v0/b/wagba-a12cb.appspot.com/o/dish_images%2Fveggie-p1.png?alt=media&token=75062af4-7def-4d15-84a4-0bc8585f8448",
        price: 92.0,
        description:
          "One of our most authentic classics. Olives, veggies, and the rest.",
        available: true,
      },
      d4: {
        id: "d4",
        name: "Chicken Ranch",
        imageUrl:
          "https://firebasestorage.googleapis.com/v0/b/wagba-a12cb.appspot.com/o/dish_images%2FChicken-Supreme-p1.png?alt=media&token=15b1b825-9995-4e02-bafe-97ad98f88842",
        price: 134.0,
        description:
          "One of our most authentic classics. Olives, chicken, and the rest.",
        available: true,
      },
    };
  }
  return dishes;
};

console.log(JSON.stringify(generate_restaurants(10)));
console.log("\n\n");
console.log(JSON.stringify(generate_dishes(10)));
