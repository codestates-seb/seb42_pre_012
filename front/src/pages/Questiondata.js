const getRandomNumber = (min, max) => {
    return parseInt(Math.random() * (Number(max) - Number(min) + 2));
  };
  
  const getParsedDate = (createdAt) => {
    return new Date(createdAt).toLocaleDateString('ko-KR');
  }
  
  const questions = [
    {
      questionId: 1,
      displayName: 'kimcoding',
      likeCnt: getRandomNumber(1,99),
      viewCnt: getRandomNumber(1,99),
      answerCnt: getRandomNumber(1,99),
      profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
        1,
        98
      )}.jpg`,
      title:
        'Error(56,10): PL/SQL: SQL Statement ignored',
      createdAt: getParsedDate('2022-02-24T16:17:47.000Z'),
      updatedAt: getParsedDate('2022-02-24T16:17:47.000Z'),
      tag: [
        {
            tagId: 1,
            tagName: 'javascript',
        },
        {
            tagId: 2,
            tagName: 'node.js',
        },
        {
            tagId: 3,
            tagName: 'api',
        },
      ],
    },
    {
      questionId: 2,
      displayName: 'parkhacker',
      likeCnt: getRandomNumber(1,99),
      viewCnt: getRandomNumber(1,99),
      answerCnt: getRandomNumber(1,99),
      profileImagePath: `https://randomuser.me/api/portraits/men/${getRandomNumber(
        1,
        98
      )}.jpg`,
      title:
        'How to change direction of a text when it comes to certain symbols',
      createdAt: getParsedDate('2022-02-25T16:17:47.000Z'),
      updatedAt: getParsedDate('2022-02-25T16:17:47.000Z'),
      tag: [
        {
            tagId: 1,
            tagName: 'javascript',
        },
        {
            tagId: 2,
            tagName: 'azure',
        },
      ],
    },
    {
      questionId: 3,
      displayName: 'leedesign',
      likeCnt: getRandomNumber(1,99),
      viewCnt: getRandomNumber(1,99),
      answerCnt: getRandomNumber(1,99),
      profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
        1,
        98
      )}.jpg`,
      title:
        'Possible to trigger animation every time user scrolls down and not trigger when scrolls up back?',
      createdAt: getParsedDate('2022-02-26T16:17:47.000Z'),
      updatedAt: getParsedDate('2022-02-26T16:17:47.000Z'),
      tag: [
        {
            tagId: 1,
            tagName: 'javascript',
        },
        {
            tagId: 2,
            tagName: 'node.js',
        },
        {
            tagId: 3,
            tagName: 'c#-3.0',
        },
        {
            tagId: 3,
            tagName: 'html',
        },
      ],
    },
    {
      questionId: 4,
      displayName: 'songfront',
      likeCnt: getRandomNumber(1,99),
      viewCnt: getRandomNumber(1,99),
      answerCnt: getRandomNumber(1,99),
      profileImagePath: `https://randomuser.me/api/portraits/men/${getRandomNumber(
        1,
        98
      )}.jpg`,
      title:
        'JS/CSS: Move element towards position while scaling it',
      createdAt: getParsedDate('2022-02-27T16:17:47.000Z'),
      updatedAt: getParsedDate('2022-02-27T16:17:47.000Z'),
      tag: [
        {
            tagId: 1,
            tagName: 'javascript',
        },
        {
            tagId: 2,
            tagName: 'node.js',
        },
        {
            tagId: 3,
            tagName: 'c#-3.0',
        },
        {
            tagId: 3,
            tagName: 'html',
        },
      ],
    },
    {
      questionId: 5,
      displayName: 'choiback',
      likeCnt: getRandomNumber(1,99),
      viewCnt: getRandomNumber(1,99),
      answerCnt: getRandomNumber(1,99),
      profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
        1,
        98
      )}.jpg`,
      title:
        'Im stuck for almost 24hrs in styled-components for button in react courses',
      createdAt: getParsedDate('2022-02-28T16:17:47.000Z'),
      updatedAt: getParsedDate('2022-02-28T16:17:47.000Z'),
      tag: [
        {
            tagId: 1,
            tagName: 'javascript',
        },
        {
            tagId: 2,
            tagName: 'node.js',
        },
        {
            tagId: 3,
            tagName: 'c#-3.0',
        },
        {
            tagId: 3,
            tagName: 'html',
        },
      ],
    },
    {
        questionId: 5,
        displayName: 'choiback',
        likeCnt: getRandomNumber(1,99),
        viewCnt: getRandomNumber(1,99),
        answerCnt: getRandomNumber(1,99),
        profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
          1,
          98
        )}.jpg`,
        title:
          'Im stuck for almost 24hrs in styled-components for button in react courses',
        createdAt: getParsedDate('2022-02-28T16:17:47.000Z'),
        updatedAt: getParsedDate('2022-02-28T16:17:47.000Z'),
        tag: [
          {
              tagId: 1,
              tagName: 'javascript',
          },
          {
              tagId: 2,
              tagName: 'node.js',
          },
          {
              tagId: 3,
              tagName: 'c#-3.0',
          },
          {
              tagId: 3,
              tagName: 'html',
          },
        ],
      },
      {
        questionId: 5,
        displayName: 'choiback',
        likeCnt: getRandomNumber(1,99),
        viewCnt: getRandomNumber(1,99),
        answerCnt: getRandomNumber(1,99),
        profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
          1,
          98
        )}.jpg`,
        title:
          'Im stuck for almost 24hrs in styled-components for button in react courses',
        createdAt: getParsedDate('2022-02-28T16:17:47.000Z'),
        updatedAt: getParsedDate('2022-02-28T16:17:47.000Z'),
        tag: [
          {
              tagId: 1,
              tagName: 'javascript',
          },
          {
              tagId: 2,
              tagName: 'node.js',
          },
          {
              tagId: 3,
              tagName: 'c#-3.0',
          },
          {
              tagId: 3,
              tagName: 'html',
          },
        ],
      },
      {
        questionId: 5,
        displayName: 'choiback',
        likeCnt: getRandomNumber(1,99),
        viewCnt: getRandomNumber(1,99),
        answerCnt: getRandomNumber(1,99),
        profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
          1,
          98
        )}.jpg`,
        title:
          'Im stuck for almost 24hrs in styled-components for button in react courses',
        createdAt: getParsedDate('2022-02-28T16:17:47.000Z'),
        updatedAt: getParsedDate('2022-02-28T16:17:47.000Z'),
        tag: [
          {
              tagId: 1,
              tagName: 'javascript',
          },
          {
              tagId: 2,
              tagName: 'node.js',
          },
          {
              tagId: 3,
              tagName: 'c#-3.0',
          },
          {
              tagId: 3,
              tagName: 'html',
          },
        ],
      },
      {
        questionId: 5,
        displayName: 'choiback',
        likeCnt: getRandomNumber(1,99),
        viewCnt: getRandomNumber(1,99),
        answerCnt: getRandomNumber(1,99),
        profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
          1,
          98
        )}.jpg`,
        title:
          'Im stuck for almost 24hrs in styled-components for button in react courses',
        createdAt: getParsedDate('2022-02-28T16:17:47.000Z'),
        updatedAt: getParsedDate('2022-02-28T16:17:47.000Z'),
        tag: [
          {
              tagId: 1,
              tagName: 'javascript',
          },
          {
              tagId: 2,
              tagName: 'node.js',
          },
          {
              tagId: 3,
              tagName: 'c#-3.0',
          },
          {
              tagId: 3,
              tagName: 'html',
          },
        ],
      },
      {
        questionId: 5,
        displayName: 'choiback',
        likeCnt: getRandomNumber(1,99),
        viewCnt: getRandomNumber(1,99),
        answerCnt: getRandomNumber(1,99),
        profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
          1,
          98
        )}.jpg`,
        title:
          'Im stuck for almost 24hrs in styled-components for button in react courses',
        createdAt: getParsedDate('2022-02-28T16:17:47.000Z'),
        updatedAt: getParsedDate('2022-02-28T16:17:47.000Z'),
        tag: [
          {
              tagId: 1,
              tagName: 'javascript',
          },
          {
              tagId: 2,
              tagName: 'node.js',
          },
          {
              tagId: 3,
              tagName: 'c#-3.0',
          },
          {
              tagId: 3,
              tagName: 'html',
          },
        ],
      },
      {
        questionId: 5,
        displayName: 'choiback',
        likeCnt: getRandomNumber(1,99),
        viewCnt: getRandomNumber(1,99),
        answerCnt: getRandomNumber(1,99),
        profileImagePath: `https://randomuser.me/api/portraits/women/${getRandomNumber(
          1,
          98
        )}.jpg`,
        title:
          'Im stuck for almost 24hrs in styled-components for button in react courses',
        createdAt: getParsedDate('2022-02-28T16:17:47.000Z'),
        updatedAt: getParsedDate('2022-02-28T16:17:47.000Z'),
        tag: [
          {
              tagId: 1,
              tagName: 'javascript',
          },
          {
              tagId: 2,
              tagName: 'node.js',
          },
          {
              tagId: 3,
              tagName: 'c#-3.0',
          },
          {
              tagId: 3,
              tagName: 'html',
          },
        ],
      },
  ];


  
  export { questions };