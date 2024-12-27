import React, { useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { useTranslation } from 'react-i18next'
import ImageBanner from '../../Shared/Image/ImageBanner'
import TourismOfferButtons from '../TourismOfferButtons'
import TourismOfferDetails from '../TourismOfferDetails'
import PersonalPanel from '../../Shared/Personal/PersonalPanel'
import PersonalLabel from '../../Shared/Personal/PersonalLabel'
import Input from '../../Shared/Controls/Input'
import { useForm } from '../../../hooks/useForm.ts'
import { Image, ImageMode } from '../../../types/image.ts'
import { AttractionType, PriceType } from '../../../types/attraction.ts'
import styles from './TourismAttractionOfferPanel.module.css'

interface Props {
    touristicPlaceId: number
    modify?: boolean
}

interface FormData {
    attractionType: AttractionType | null
    name: string
    description: string
    price: number | null
    priceType: PriceType
    prepayment: boolean
    cancelReservationDays: number | null
    maxPeopleNumber: number | null
    maxItems: number | null
    dateFrom: Date | null
    dateTo: Date | null
    hourFrom: string
    hourTo: string
    daysReservationBefore: number | null
}

const TourismAttractionOfferPanel = ({ touristicPlaceId, modify }: Props) => {
    const { t } = useTranslation()
    const navigate = useNavigate()
    const { attractionId } = useLocation().state
    const [images, setImages] = useState<Image[]>([])
    const [files, setFiles] = useState<File[]>([])
    const [loading, setLoading] = useState(false)
    const { formData, error, setError, handleChange, setFormValues } = useForm<FormData>({
        initialValues: {
            attractionType: AttractionType.OTHER,
            name: '',
            description: '',
            price: null,
            priceType: PriceType.HOUR,
            prepayment: false,
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

    const handleSave = async (e: React.FormEvent) => {}

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
                                label={<PersonalLabel text={t('tourism.touristic-place-attraction-name-and-type')} />}
                                firstInput={
                                    <Input
                                        type={'text'}
                                        name={'name'}
                                        placeholder={t('tourism.')}
                                        value={''}
                                        onChange={null}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                secondInput={
                                    <Input
                                        type={'text'}
                                        name={'name'}
                                        placeholder={t('tourism.')}
                                        value={''}
                                        onChange={null}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                touristicPlace
                            />
                        }
                        secondPanel={
                            <PersonalPanel
                                label={<PersonalLabel text={t('tourism.touristic-place-stay-offer-information')} />}
                                firstInput={
                                    <Input
                                        type={'text'}
                                        name={'name'}
                                        placeholder={t('tourism.')}
                                        value={''}
                                        onChange={null}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                touristicPlace
                            />
                        }
                        thirdPanel={
                            <PersonalPanel
                                label={
                                    <PersonalLabel text={t('tourism.touristic-place-stay-offer-availability-range')} />
                                }
                                firstInput={
                                    <Input
                                        type={'text'}
                                        name={'name'}
                                        placeholder={t('tourism.')}
                                        value={''}
                                        onChange={null}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                secondInput={
                                    <Input
                                        type={'text'}
                                        name={'name'}
                                        placeholder={t('tourism.')}
                                        value={''}
                                        onChange={null}
                                        required={true}
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
                                label={
                                    <PersonalLabel
                                        text={t('tourism.touristic-place-stay-offer-name-and-description')}
                                    />
                                }
                                firstInput={
                                    <Input
                                        type={'text'}
                                        name={'name'}
                                        placeholder={t('tourism.')}
                                        value={''}
                                        onChange={null}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                secondInput={
                                    <Input
                                        type={'text'}
                                        name={'name'}
                                        placeholder={t('tourism.')}
                                        value={''}
                                        onChange={null}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                touristicPlace
                            />
                        }
                        secondPanel={
                            <PersonalPanel
                                label={<PersonalLabel text={t('tourism.touristic-place-stay-offer-information')} />}
                                firstInput={
                                    <Input
                                        type={'text'}
                                        name={'name'}
                                        placeholder={t('tourism.')}
                                        value={''}
                                        onChange={null}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                secondInput={
                                    <Input
                                        type={'text'}
                                        name={'name'}
                                        placeholder={t('tourism.')}
                                        value={''}
                                        onChange={null}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                thirdInput={
                                    <Input
                                        type={'text'}
                                        name={'name'}
                                        placeholder={t('tourism.')}
                                        value={''}
                                        onChange={null}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                touristicPlace
                            />
                        }
                        thirdPanel={
                            <PersonalPanel
                                label={
                                    <PersonalLabel text={t('tourism.touristic-place-stay-offer-availability-range')} />
                                }
                                firstInput={
                                    <Input
                                        type={'text'}
                                        name={'name'}
                                        placeholder={t('tourism.')}
                                        value={''}
                                        onChange={null}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                secondInput={
                                    <Input
                                        type={'text'}
                                        name={'name'}
                                        placeholder={t('tourism.')}
                                        value={''}
                                        onChange={null}
                                        required={true}
                                        disabled={loading}
                                    />
                                }
                                thirdInput={
                                    <Input
                                        type={'text'}
                                        name={'name'}
                                        placeholder={t('tourism.')}
                                        value={''}
                                        onChange={null}
                                        required={true}
                                        disabled={loading}
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
