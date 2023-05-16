FROM nginx

EXPOSE 3001

COPY ./default.conf /etc/nginx/conf.d/default.conf

COPY --from=builder /app/build  /usr/share/nginx/html