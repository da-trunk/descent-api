// import { ReactQueryDevtools } from '@tanstack/react-query-devtools'
import { gql } from "graphql-tag";
import { useItemsQuery } from "./useItemsQuery"
import Items from "./Items.js"

import './App.css';
// import Items from './Items'

const GET_ITEMS = gql`
query {
  allItemsByHero {
    itemId
    hero {
      id
      traits {
        health
      }
    }
  }
}
`;

const App = () => {
  const { data, isLoading, error } = useItemsQuery('items', GET_ITEMS);
  if (isLoading) return "loading ...";
  if (error) return "error...";
  return (<div className="App">
    <Items data={data} />
    {data.allItemsByHero.map(item => (
      <div key={item.hero.id}>{item.hero.id}</div>
    ))}
  </div>
  );
};

export default App;