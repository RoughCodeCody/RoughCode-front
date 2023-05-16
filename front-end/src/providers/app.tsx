import type { ReactNode } from "react";

import { QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { ErrorBoundary } from "react-error-boundary";

import { ErrorFallback } from "@/components/fallbacks";
import { queryClient } from "@/lib/react-query";

type AppProviderProps = {
  children: ReactNode;
};

export const AppProvider = ({ children }: AppProviderProps) => {
  return (
    <ErrorBoundary fallback={<ErrorFallback />}>
      <QueryClientProvider client={queryClient}>
        {process.env.NODE_ENV !== "test" && <ReactQueryDevtools />}
        {children}
      </QueryClientProvider>
    </ErrorBoundary>
  );
};
