import { useQuery } from "react-query";
import { request, GraphQLClient } from "graphql-request";


export const useItemsQuery = (key, query, variables, config = {}) => {
    const endpoint = "http://localhost:9080/graphql";

    const fetchData = async () => await request(endpoint, query, variables);

    return useQuery(key, fetchData, config);
};
