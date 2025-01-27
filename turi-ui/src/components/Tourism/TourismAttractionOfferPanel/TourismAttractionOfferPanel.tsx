import React, { useEffect, useState } from 'react'
import { useId } from '../../../hooks/shared/useId.ts'
import { handle } from '../../../utils/handle.ts'
import { useForm } from '../../../hooks/shared/useForm.ts'
import { useHooks } from '../../../hooks/shared/useHooks.ts'
import { handleFormError } from '../../../utils/handleFormError.ts'
import Label from '../../Shared/Controls/Label'
import Input from '../../Shared/Controls/Input'
import Checkbox from '../../Shared/Controls/Checkbox'
import ImageBanner from '../../Shared/Image/ImageBanner'
import PersonalPanel from '../../Shared/Personal/PersonalPanel'
import TourismOfferButtons from '../TourismOfferButtons'
import TourismOfferDetails from '../TourismOfferDetails'
import TourismPriceTypeSelect from '../TourismPriceTypeSelect'
import TourismAttractionTypeSelect from '../TourismAttractionTypeSelect'
import TourismTouristicPlaceCheckbox from '../TourismTouristicPlaceCheckbox'
import { Image, ImageMode } from '../../../types/image.ts'
import { Attraction, AttractionType, PriceType } from '../../../types/attraction.ts'
import { imageService } from '../../../services/imageService.ts'
import { attractionService } from '../../../services/attractionService.ts'
import styles from './TourismAttractionOfferPanel.module.css'

interface Props {
    touristicPlaceId: number
    modify?: boolean
}

interface FormData {
    attractionType: AttractionType
    name: string
    description: string
    price: number | null
    priceType: PriceType
    prepayment: boolean
    cancelReservation: boolean
    cancelReservationDays: number | null
    maxPeopleNumber: number | null
    maxItems: number | null
    dateFrom: Date | null
    dateTo: Date | null
    hourFrom: string
    hourTo: string
    daysReservationBefore: number | null
}

const TourismAttractionOfferPanel = ({ touristicPlaceId, modify = false }: Props) => {
    const { t, navigate, location } = useHooks()
    const { attractionId = null } = location.state || {}
    const [images, setImages] = useState<Image[]>([])
    const [files, setFiles] = useState<File[]>([])
    const [loading, setLoading] = useState(false)
    const { formData, error, setError, handleChange, setFormValues, updateFormData } = useForm<FormData>({
        initialValues: {
            attractionType: AttractionType.UNASSIGNED,
            name: '',
            description: '',
            price: null,
            priceType: PriceType.UNASSIGNED,
            prepayment: false,
            cancelReservation: false,
            cancelReservationDays: null,
            maxPeopleNumber: null,
            maxItems: null,
            dateFrom: null,
            dateTo: null,
            hourFrom: '',
            hourTo: '',
            daysReservationBefore: null
        }
    })

    useId(attractionId, '/tourism', modify)

    useEffect(() => {
        const fetchData = async () => {
            const attractionResponse = await attractionService.getById(attractionId)
            if (attractionResponse.status === 200) {
                const attractionData: Attraction = await attractionResponse.json()
                setFormValues({
                    attractionType: AttractionType[attractionData.attractionType as keyof typeof AttractionType],
                    name: attractionData.name,
                    description: attractionData.description,
                    price: attractionData.price,
                    priceType: PriceType[attractionData.priceType as unknown as keyof typeof PriceType],
                    prepayment: attractionData.prepayment,
                    cancelReservationDays: attractionData.cancelReservationDays,
                    maxPeopleNumber: attractionData.maxPeopleNumber,
                    maxItems: attractionData.maxItems,
                    dateFrom: convertToDate(attractionData.dateFrom),
                    dateTo: convertToDate(attractionData.dateTo),
                    hourFrom: convertToTime(attractionData.hourFrom),
                    hourTo: convertToTime(attractionData.hourTo),
                    daysReservationBefore: attractionData.daysReservationBefore
                })
                const imagesResponse = await imageService.getAllByAttractionId(attractionId)
                if (imagesResponse.status === 200) {
                    const imagesData = await imagesResponse.json()
                    setImages(imagesData)
                }
            }
        }

        const convertToDate = (dateArray: any): Date | null => {
            if (!dateArray) return null
            const [year, month, day] = dateArray
            return new Date(Date.UTC(year, month - 1, day))
        }

        const convertToTime = (dateArray: any): string | undefined => {
            if (!dateArray) return undefined
            const [hour, minute] = dateArray
            const formattedHour = ('0' + hour).slice(-2)
            const formattedMinute = ('0' + minute).slice(-2)
            return `${formattedHour}:${formattedMinute}`
        }

        attractionId && fetchData().catch((error) => error)
    }, [])

    const handleSave = async (e: React.FormEvent) => {
        handle(e, setLoading, setError)

        if (
            formData.attractionType.toString() === AttractionType.UNASSIGNED.toString() ||
            formData.priceType.toString() === PriceType.UNASSIGNED.toString()
        )
            return handleFormError(setError, setLoading, 'tourism.touristic-place-attraction-type-error')

        if (
            formData.price === null ||
            formData.dateFrom === null ||
            formData.dateTo === null ||
            formData.daysReservationBefore === null
        )
            return handleFormError(setError, setLoading, 'tourism.touristic-place-stay-offer-create-fields')

        if (formData.dateFrom && formData.dateTo && formData.dateFrom > formData.dateTo)
            return handleFormError(setError, setLoading, 'tourism.touristic-place-attraction-data-error')

        if (formData.hourFrom && formData.hourTo && formData.hourFrom > formData.hourTo)
            return handleFormError(setError, setLoading, 'tourism.touristic-place-attraction-hour-error')

        if (modify) {
            const attractionResponse = await attractionService.update(attractionId, {
                touristicPlaceId: touristicPlaceId,
                attractionType: formData.attractionType,
                name: formData.name,
                description: formData.description,
                price: formData.price,
                priceType: formData.priceType,
                prepayment: formData.prepayment,
                cancelReservationDays: formData.cancelReservationDays || undefined,
                maxPeopleNumber: formData.maxPeopleNumber || undefined,
                maxItems: formData.maxItems || undefined,
                dateFrom: formData.dateFrom,
                dateTo: formData.dateTo,
                hourFrom: formData.hourFrom,
                hourTo: formData.hourTo,
                daysReservationBefore: formData.daysReservationBefore
            })
            setLoading(false)
            if (attractionResponse.status !== 200)
                return handleFormError(setError, setLoading, 'tourism.touristic-place-attraction-save-error')
        } else {
            const attractionResponse = await attractionService.create({
                touristicPlaceId: touristicPlaceId,
                attractionType: formData.attractionType,
                name: formData.name,
                description: formData.description,
                price: formData.price,
                priceType: formData.priceType,
                prepayment: formData.prepayment,
                cancelReservationDays: formData.cancelReservationDays || undefined,
                maxPeopleNumber: formData.maxPeopleNumber || undefined,
                maxItems: formData.maxItems || undefined,
                dateFrom: formData.dateFrom,
                dateTo: formData.dateTo,
                hourFrom: formData.hourFrom,
                hourTo: formData.hourTo,
                daysReservationBefore: formData.daysReservationBefore
            })
            if (attractionResponse.status === 200) {
                const attractionData: Attraction = await attractionResponse.json()
                files.forEach((file) => {
                    imageService.upload(file, ImageMode.ATTRACTION, attractionData.attractionId)
                })
                navigate('/tourism')
            } else return handleFormError(setError, setLoading, 'tourism.touristic-place-attraction-create-error')
        }
    }

    return (
        <form
            className={styles.form}
            onSubmit={handleSave}
        >
            <div className={styles.panel}>
                {!attractionId && (
                    <ImageBanner
                        files={files}
                        uploadFile={(file: File) => setFiles((prevFiles) => [...prevFiles, file])}
                        removeFile={(file: File) =>
                            setFiles((prevFiles) => prevFiles.filter((prevFile) => prevFile !== file))
                        }
                    />
                )}
                {attractionId && (
                    <ImageBanner
                        images={images}
                        uploadImage={(image: Image) => setImages((prevImages) => [...prevImages, image])}
                        mode={ImageMode.ATTRACTION}
                        id={attractionId}
                        removeImage={(imageId: number) =>
                            setImages((prevImage) => prevImage.filter((image) => image.imageId !== imageId))
                        }
                    />
                )}
                <div className={styles.content}>
                    <TourismOfferDetails
                        firstPanel={
                            <PersonalPanel
                                label={<Label text={t('tourism.touristic-place-attraction-name-and-type')} />}
                                firstInput={
                                    <Input
                                        type={'text'}
                                        name={'name'}
                                        placeholder={t('tourism.touristic-place-attraction-name')}
                                        value={formData.name}
                                        onChange={handleChange}
                                        maxLength={50}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                secondInput={
                                    <TourismAttractionTypeSelect
                                        value={formData.attractionType}
                                        onChange={(attractionType: AttractionType) =>
                                            updateFormData((prevData) => ({
                                                ...prevData,
                                                attractionType: attractionType
                                            }))
                                        }
                                        disabled={loading}
                                    />
                                }
                                touristicPlace
                            />
                        }
                        secondPanel={
                            <PersonalPanel
                                label={<Label text={t('tourism.touristic-place-attraction-description')} />}
                                firstInput={
                                    <Input
                                        type={'text'}
                                        name={'description'}
                                        placeholder={t('tourism.touristic-place-attraction-description')}
                                        value={formData.description}
                                        onChange={handleChange}
                                        maxLength={255}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                secondInput={
                                    <Input
                                        type={'number'}
                                        name={'daysReservationBefore'}
                                        placeholder={t('tourism.touristic-place-attraction-days-before')}
                                        value={formData.daysReservationBefore}
                                        onChange={handleChange}
                                        maxLength={255}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                touristicPlace
                            />
                        }
                        thirdPanel={
                            <PersonalPanel
                                label={<Label text={t('tourism.touristic-place-attraction-availability-range')} />}
                                firstInput={
                                    <Input
                                        type={'date'}
                                        name={'dateFrom'}
                                        placeholder={t('tourism.touristic-place-attraction-availability-range-from')}
                                        value={formData.dateFrom}
                                        onChange={handleChange}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                secondInput={
                                    <Input
                                        type={'date'}
                                        name={'dateTo'}
                                        placeholder={t('tourism.touristic-place-attraction-availability-range-to')}
                                        value={formData.dateTo}
                                        onChange={handleChange}
                                        required={false}
                                        disabled={loading}
                                    />
                                }
                                touristicPlace
                            />
                        }
                    />
                    <TourismOfferDetails
                        firstPanel={
                            <PersonalPanel
                                label={<Label text={t('tourism.touristic-place-attraction-price-type-and-price')} />}
                                firstInput={
                                    <TourismPriceTypeSelect
                                        value={formData.priceType}
                                        onChange={(priceType: PriceType) =>
                                            updateFormData((prevData) => ({
                                                ...prevData,
                                                priceType: priceType,
                                                maxItems: null,
                                                maxPeopleNumber: null
                                            }))
                                        }
                                        disabled={loading}
                                    />
                                }
                                secondInput={
                                    <Input
                                        type={'number'}
                                        name={'price'}
                                        placeholder={t('tourism.touristic-place-attraction-price')}
                                        value={formData.price}
                                        onChange={handleChange}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                touristicPlace
                            />
                        }
                        secondPanel={
                            <PersonalPanel
                                label={
                                    <Label
                                        text={t(
                                            'tourism.touristic-place-attraction-max-items-people-number-from-to-hour'
                                        )}
                                    />
                                }
                                firstInput={
                                    formData.priceType.toString() === PriceType.ITEM.toString() ? (
                                        <Input
                                            type={'number'}
                                            name={'maxItems'}
                                            placeholder={t('tourism.touristic-place-attraction-max-items-number')}
                                            value={formData.maxItems}
                                            onChange={handleChange}
                                            required={true}
                                            disabled={loading}
                                        />
                                    ) : formData.priceType.toString() === PriceType.PERSON.toString() ? (
                                        <Input
                                            type={'number'}
                                            name={'maxPeopleNumber'}
                                            placeholder={t('tourism.touristic-place-attraction-max-people-number')}
                                            value={formData.maxPeopleNumber}
                                            onChange={handleChange}
                                            required={true}
                                            disabled={loading}
                                        />
                                    ) : null
                                }
                                secondInput={
                                    <Input
                                        type={'time'}
                                        name={'hourFrom'}
                                        placeholder={t('tourism.touristic-place-attraction-from-hour')}
                                        value={formData.hourFrom}
                                        onChange={handleChange}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                thirdInput={
                                    <Input
                                        type={'time'}
                                        name={'hourTo'}
                                        placeholder={t('tourism.touristic-place-attraction-to-hour')}
                                        value={formData.hourTo}
                                        onChange={handleChange}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                touristicPlace
                            />
                        }
                        thirdPanel={
                            <PersonalPanel
                                label={<Label text={t('tourism.touristic-place-attraction-reservation')} />}
                                firstInput={
                                    <TourismTouristicPlaceCheckbox
                                        checkbox={
                                            <Checkbox
                                                checked={formData.prepayment}
                                                onChange={() =>
                                                    updateFormData((prevData) => ({
                                                        ...prevData,
                                                        prepayment: !prevData.prepayment
                                                    }))
                                                }
                                                text={t('tourism.touristic-place-attraction-reservation-prepayment')}
                                            />
                                        }
                                    />
                                }
                                secondInput={
                                    <TourismTouristicPlaceCheckbox
                                        checkbox={
                                            <Checkbox
                                                checked={formData.cancelReservation}
                                                onChange={() =>
                                                    updateFormData((prevData) => ({
                                                        ...prevData,
                                                        cancelReservation: !prevData.cancelReservation,
                                                        cancelReservationDays: !prevData.cancelReservation
                                                            ? 0
                                                            : prevData.cancelReservationDays
                                                    }))
                                                }
                                                text={t('tourism.touristic-place-attraction-reservation-cancel')}
                                            />
                                        }
                                    />
                                }
                                thirdInput={
                                    <Input
                                        type="number"
                                        name="cancelReservationDays"
                                        placeholder={t('tourism.touristic-place-attraction-reservation-days')}
                                        value={formData.cancelReservationDays}
                                        onChange={handleChange}
                                        required={false}
                                        disabled={!formData.cancelReservation || loading}
                                    />
                                }
                                touristicPlace
                            />
                        }
                    />
                </div>
                <TourismOfferButtons
                    modify={modify || false}
                    id={attractionId}
                    mode={'attraction'}
                    error={error}
                />
            </div>
        </form>
    )
}

export default TourismAttractionOfferPanel
