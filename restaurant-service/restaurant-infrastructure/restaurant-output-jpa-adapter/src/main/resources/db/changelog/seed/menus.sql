ALTER TABLE public.menus
    DISABLE TRIGGER ALL;

INSERT INTO public.menus (id, restaurant_id)
VALUES ('5c6a5b43-b39e-4faa-b39e-0c1b8617294d', '49849cf3-5ef8-491b-ab6c-5d35cb007e38');

INSERT INTO public.menus (id, restaurant_id)
VALUES ('dd7ed88f-5e3d-432b-9f22-9479e42aae7b', 'ce3e7069-09dc-448b-8a33-0a7c503576ab');

ALTER TABLE public.menus
    enable TRIGGER ALL;
