import Head from "next/head";

import { Spinner } from "@/components/elements";
import { Notifications } from "@/features/notifications";
import { UnauthenticatedLanding } from "@/features/misc";
import { useUser } from "@/features/auth";

export default function Home() {
  const userQuery = useUser();
  return (
    <>
      <Head>
        <title>개발새발</title>
        <meta name="description" content="개발새발" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/favicon.ico" />
      </Head>
      {userQuery.isLoading && <></>}
      {userQuery.data && userQuery.data.nickname.length === 0 && (
        <UnauthenticatedLanding />
      )}
      {userQuery.data && userQuery.data.nickname.length !== 0 && (
        <Notifications />
      )}
    </>
  );
}
