const getRandomNumber = (min, max) => {
  return parseInt(Math.random() * (Number(max) - Number(min) + 2));
};

const getParsedDate = (createdAt) => {
  return new Date(createdAt).toLocaleDateString("ko-KR");
};

const questions = [
  {
    id: 1,
    displayName: "kimcoding",
    profileImg: "K",
    likeCnt: getRandomNumber(1, 99),
    viewCnt: getRandomNumber(1, 99),
    answerCnt: getRandomNumber(1, 99),
    profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
      1,
      98
    )}.jpg`,
    title:
      "My set class method runs perfectly in other phones but when I tested it on Xiaomi and Huawei phones I get an error saying that the class is not found",
    content: `I have single page application that is built using Angularjs and integrated with Keycloak for authentication and authorization.
    I am able to login into my application, get loggedin user roles etc. goes The moment refresh token call, it always returns in my else case, and user logout of the application. Though the token valid time is set very high.
    I need to update the token, if user has opened the app. In case of failure or expire token i need to logout the user. if (refreshed) always returns false.`,
    createdAt: getParsedDate("2022-02-24T16:17:47.000Z"),
    updatedAt: getParsedDate("2022-02-24T16:17:47.000Z"),
    tag: [
      {
        tagId: 1,
        tagName: "javascript",
      },
      {
        tagId: 2,
        tagName: "node.js",
      },
      {
        tagId: 3,
        tagName: "api",
      },
    ],
    questionComment: [
      {
        id: 1,
        content: `Your code seems fine to me. Could you post a complete minimal sample (as OlegBezr did), so that we can run your exact code on our side?`,
        displayName: `deczaloth`,
        createdAt: getParsedDate("2022-02-24T16:17:47.000Z"),
      },
      {
        id: 2,
        content:
          "mean using separate scrollable widget on body? can you include a gif what exactly you are trying to get",
        displayName: "Yeasin Sheikh",
        createdAt: getParsedDate("2022-02-24T16:17:47.000Z"),
      },
      {
        id: 3,
        content: `Something doesn't make sense. Why didn't you try removing CustomScrollView altogether and why your listview has one item NavigationList?`,
        displayName: "Rahul",
        createdAt: getParsedDate("2022-02-24T16:17:47.000Z"),
      },
    ],
    answered: [
      {
        id: 1,
        content: `So I approached it differently. Since the parent list is only scrollable with the scroll bar I decided to disable the scroll bar for the parent list. It solves the issue I had.

        But it still seems a bug to me that boxes with height added to a CustomScrollView with a SliverFillRemaining create overflow.

        For now I will accept this as the answer.`,
        editedAt: getParsedDate("2022-02-24T16:17:47.000Z"),
        answeredAt: getParsedDate("2022-02-24T16:17:47.000Z"),
        displayName: "Sachin Kumar",
        profileImg: "S",
        likeCnt: getRandomNumber(1, 99),
        answeredComment: [
          {
            id: 1,
            content:
              "Your code seems fine to me. Could you post a complete minimal sample (as OlegBezr did), so that we can run your exact code on our side?",
            displayName: "deczaloth",
            createdAt: getParsedDate("2022-02-24T16:17:47.000Z"),
          },
        ],
      },
      {
        id: 2,
        content: `So I approached it differently. Since the parent list is only scrollable with the scroll bar I decided to disable the scroll bar for the parent list. It solves the issue I had.

        But it still seems a bug to me that boxes with height added to a CustomScrollView with a SliverFillRemaining create overflow.

        For now I will accept this as the answer.`,
        editedAt: getParsedDate("2022-02-24T16:17:47.000Z"),
        answeredAt: getParsedDate("2022-02-24T16:17:47.000Z"),
        displayName: "Sachin Kumar",
        profileImg: "S",
        likeCnt: getRandomNumber(1, 99),
        answeredComment: [
          {
            id: 1,
            content:
              "Your code seems fine to me. Could you post a complete minimal sample (as OlegBezr did), so that we can run your exact code on our side?",
            displayName: "deczaloth",
            createdAt: getParsedDate("2022-02-24T16:17:47.000Z"),
          },
        ],
      },
    ],
  },
  {
    id: 2,
    displayName: "parkhacker",
    profileImg: "P",
    likeCnt: getRandomNumber(1, 99),
    viewCnt: getRandomNumber(1, 99),
    answerCnt: getRandomNumber(1, 99),
    profileImagePath: `https://randomuser.me/api/portraits/men/${getRandomNumber(
      1,
      98
    )}.jpg`,
    title: "How to change direction of a text when it comes to certain symbols",
    content:
      "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting",
    createdAt: getParsedDate("2022-02-25T16:17:47.000Z"),
    updatedAt: getParsedDate("2022-02-25T16:17:47.000Z"),
    tag: [
      {
        tagId: 1,
        tagName: "javascript",
      },
      {
        tagId: 2,
        tagName: "azure",
      },
    ],
    questionComment: [
      {
        id: 1,
        content: `Your code seems fine to me. Could you post a complete minimal sample (as OlegBezr did), so that we can run your exact code on our side?`,
        displayName: `deczaloth`,
        createdAt: getParsedDate("2022-02-24T16:17:47.000Z"),
      },
      {
        id: 2,
        content:
          "mean using separate scrollable widget on body? can you include a gif what exactly you are trying to get",
        displayName: "Yeasin Sheikh",
        createdAt: getParsedDate("2022-02-24T16:17:47.000Z"),
      },
      {
        id: 3,
        content: `Something doesn't make sense. Why didn't you try removing CustomScrollView altogether and why your listview has one item NavigationList?`,
        displayName: "Rahul",
        createdAt: getParsedDate("2022-02-24T16:17:47.000Z"),
      },
    ],
    answered: [
      {
        id: 1,
        title: `So I approached it differently. Since the parent list is only scrollable with the scroll bar I decided to disable the scroll bar for the parent list. It solves the issue I had.

        But it still seems a bug to me that boxes with height added to a CustomScrollView with a SliverFillRemaining create overflow.

        For now I will accept this as the answer.`,
        editedAt: getParsedDate("2022-02-24T16:17:47.000Z"),
        answeredAt: getParsedDate("2022-02-24T16:17:47.000Z"),
        displayName: "Sachin Kumar",
        profileImg: "S",
        likeCnt: getRandomNumber(1, 99),
        answeredComment: [
          {
            id: 1,
            content:
              "Your code seems fine to me. Could you post a complete minimal sample (as OlegBezr did), so that we can run your exact code on our side?",
            displayName: "deczaloth",
            createdAt: getParsedDate("2022-02-24T16:17:47.000Z"),
          },
        ],
      },
    ],
  },
  {
    id: 3,
    displayName: "leedesign",
    likeCnt: getRandomNumber(1, 99),
    viewCnt: getRandomNumber(1, 99),
    answerCnt: getRandomNumber(1, 99),
    profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
      1,
      98
    )}.jpg`,
    title:
      "Possible to trigger animation every time user scrolls down and not trigger when scrolls up back?",
    content: `It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here'`,
    createdAt: getParsedDate("2022-02-26T16:17:47.000Z"),
    updatedAt: getParsedDate("2022-02-26T16:17:47.000Z"),
    tag: [
      {
        tagId: 1,
        tagName: "javascript",
      },
      {
        tagId: 2,
        tagName: "node.js",
      },
      {
        tagId: 3,
        tagName: "c#-3.0",
      },
      {
        tagId: 4,
        tagName: "html",
      },
    ],
  },
  {
    id: 4,
    displayName: "songfront",
    likeCnt: getRandomNumber(1, 99),
    viewCnt: getRandomNumber(1, 99),
    answerCnt: getRandomNumber(1, 99),
    profileImagePath: `https://randomuser.me/api/portraits/men/${getRandomNumber(
      1,
      98
    )}.jpg`,
    title: "JS/CSS: Move element towards position while scaling it",
    content: `There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable.`,
    createdAt: getParsedDate("2022-02-27T16:17:47.000Z"),
    updatedAt: getParsedDate("2022-02-27T16:17:47.000Z"),
    tag: [
      {
        tagId: 1,
        tagName: "javascript",
      },
      {
        tagId: 2,
        tagName: "node.js",
      },
      {
        tagId: 3,
        tagName: "c#-3.0",
      },
      {
        tagId: 4,
        tagName: "html",
      },
    ],
  },
  {
    id: 5,
    displayName: "choiback",
    likeCnt: getRandomNumber(1, 99),
    viewCnt: getRandomNumber(1, 99),
    answerCnt: getRandomNumber(1, 99),
    profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
      1,
      98
    )}.jpg`,
    title:
      "Im stuck for almost 24hrs in styled-components for button in react courses",
    content: `If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary,`,
    createdAt: getParsedDate("2022-02-28T16:17:47.000Z"),
    updatedAt: getParsedDate("2022-02-28T16:17:47.000Z"),
    tag: [
      {
        tagId: 1,
        tagName: "javascript",
      },
      {
        tagId: 2,
        tagName: "node.js",
      },
      {
        tagId: 3,
        tagName: "c#-3.0",
      },
      {
        tagId: 4,
        tagName: "html",
      },
    ],
  },
  {
    id: 6,
    displayName: "choiback",
    likeCnt: getRandomNumber(1, 99),
    viewCnt: getRandomNumber(1, 99),
    answerCnt: getRandomNumber(1, 99),
    profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
      1,
      98
    )}.jpg`,
    title:
      "Im stuck for almost 24hrs in styled-components for button in react courses",
    content: `The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from "de Finibus Bonorum et Malorum" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.`,
    createdAt: getParsedDate("2022-02-28T16:17:47.000Z"),
    updatedAt: getParsedDate("2022-02-28T16:17:47.000Z"),
    tag: [
      {
        tagId: 1,
        tagName: "javascript",
      },
      {
        tagId: 2,
        tagName: "node.js",
      },
      {
        tagId: 3,
        tagName: "c#-3.0",
      },
      {
        tagId: 4,
        tagName: "html",
      },
    ],
  },
  {
    id: 7,
    displayName: "choiback",
    likeCnt: getRandomNumber(1, 99),
    viewCnt: getRandomNumber(1, 99),
    answerCnt: getRandomNumber(1, 99),
    profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
      1,
      98
    )}.jpg`,
    title:
      "Im stuck for almost 24hrs in styled-components for button in react courses",
    content: `end to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free fr`,
    createdAt: getParsedDate("2022-02-28T16:17:47.000Z"),
    updatedAt: getParsedDate("2022-02-28T16:17:47.000Z"),
    tag: [
      {
        tagId: 1,
        tagName: "javascript",
      },
      {
        tagId: 2,
        tagName: "node.js",
      },
      {
        tagId: 3,
        tagName: "c#-3.0",
      },
      {
        tagId: 4,
        tagName: "html",
      },
    ],
  },
  {
    id: 8,
    displayName: "choiback",
    likeCnt: getRandomNumber(1, 99),
    viewCnt: getRandomNumber(1, 99),
    answerCnt: getRandomNumber(1, 99),
    profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
      1,
      98
    )}.jpg`,
    title:
      "Im stuck for almost 24hrs in styled-components for button in react courses",
    content:
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
    createdAt: getParsedDate("2022-02-28T16:17:47.000Z"),
    updatedAt: getParsedDate("2022-02-28T16:17:47.000Z"),
    tag: [
      {
        tagId: 1,
        tagName: "javascript",
      },
      {
        tagId: 2,
        tagName: "node.js",
      },
      {
        tagId: 3,
        tagName: "c#-3.0",
      },
      {
        tagId: 4,
        tagName: "html",
      },
    ],
  },
  {
    id: 9,
    displayName: "choiback",
    likeCnt: getRandomNumber(1, 99),
    viewCnt: getRandomNumber(1, 99),
    answerCnt: getRandomNumber(1, 99),
    profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
      1,
      98
    )}.jpg`,
    title:
      "Im stuck for almost 24hrs in styled-components for button in react courses",
    content:
      "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?",
    createdAt: getParsedDate("2022-02-28T16:17:47.000Z"),
    updatedAt: getParsedDate("2022-02-28T16:17:47.000Z"),
    tag: [
      {
        tagId: 1,
        tagName: "javascript",
      },
      {
        tagId: 2,
        tagName: "node.js",
      },
      {
        tagId: 3,
        tagName: "c#-3.0",
      },
      {
        tagId: 4,
        tagName: "html",
      },
    ],
  },
  {
    id: 10,
    displayName: "choiback",
    likeCnt: getRandomNumber(1, 99),
    viewCnt: getRandomNumber(1, 99),
    answerCnt: getRandomNumber(1, 99),
    profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
      1,
      98
    )}.jpg`,
    title:
      "Im stuck for almost 24hrs in styled-components for button in react courses",
    content:
      "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?",
    createdAt: getParsedDate("2022-02-28T16:17:47.000Z"),
    updatedAt: getParsedDate("2022-02-28T16:17:47.000Z"),
    tag: [
      {
        tagId: 1,
        tagName: "javascript",
      },
      {
        tagId: 2,
        tagName: "node.js",
      },
      {
        tagId: 3,
        tagName: "c#-3.0",
      },
      {
        tagId: 4,
        tagName: "html",
      },
    ],
  },
  {
    id: 11,
    displayName: "choiback",
    likeCnt: getRandomNumber(1, 99),
    viewCnt: getRandomNumber(1, 99),
    answerCnt: getRandomNumber(1, 99),
    profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
      1,
      98
    )}.jpg`,
    title:
      "Im stuck for almost 24hrs in styled-components for button in react courses",
    content:
      "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.",
    createdAt: getParsedDate("2022-02-28T16:17:47.000Z"),
    updatedAt: getParsedDate("2022-02-28T16:17:47.000Z"),
    tag: [
      {
        tagId: 1,
        tagName: "javascript",
      },
      {
        tagId: 2,
        tagName: "node.js",
      },
      {
        tagId: 3,
        tagName: "c#-3.0",
      },
      {
        tagId: 4,
        tagName: "html",
      },
    ],
  },
];

export { questions };
