import { apiSlice } from "./apiSlice";

const AUTH_URL = "/users";

export const authapiSlice = apiSlice.injectEndpoints({
  endpoints: (builder) => ({
    login: builder.mutation({
      query: (data) => ({
        url: `${AUTH_URL}/login`,
        method: "POST",
        body: data,
        credentials: "include",
      }),
    }),
  }),
});

export const { useLoginMutation } = authapiSlice;
