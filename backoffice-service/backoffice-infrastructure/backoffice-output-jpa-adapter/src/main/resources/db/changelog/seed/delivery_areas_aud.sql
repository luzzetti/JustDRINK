ALTER TABLE public.delivery_areas_aud
    DISABLE TRIGGER ALL;

INSERT INTO public.delivery_areas_aud (id, rev, revtype, polygon)
VALUES ('ce3e7069-09dc-448b-8a33-0a7c503576ab', 4, 0,
        '0103000000010000000500000088D317B45C8629401C59BCEFD6F8444087D317B41384294044597CB075FE444087D317B4A5B52940B0F901330CFF444087D317B4C3C429409CE4B46A59F8444088D317B45C8629401C59BCEFD6F84440');

ALTER TABLE public.delivery_areas_aud
    enable TRIGGER ALL;